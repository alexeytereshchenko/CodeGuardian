package io.github.alexeytereshchenko.guardian;

import io.github.alexeytereshchenko.guardian.extention.ErrorProneExtension;
import io.github.alexeytereshchenko.guardian.extention.GuardianCheckStyleExtension;
import io.github.alexeytereshchenko.guardian.extention.GuardianExtension;
import io.github.alexeytereshchenko.guardian.meta.TaskName;
import io.github.alexeytereshchenko.guardian.task.DownloadCheckstyleFile;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;
import net.ltgt.gradle.errorprone.CheckSeverity;
import net.ltgt.gradle.errorprone.ErrorProneCompilerArgumentProvider;
import net.ltgt.gradle.errorprone.ErrorProneOptions;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.plugins.quality.Checkstyle;
import org.gradle.api.plugins.quality.CheckstyleExtension;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.language.base.internal.plugins.CleanRule;
import org.jetbrains.annotations.NotNull;

public class GuardianPlugin implements Plugin<Project> {
  @Override
  public void apply(@NotNull Project project) {
    project.getExtensions().create("guardian", GuardianExtension.class);

    project.afterEvaluate(evaluatedProject -> {
      GuardianExtension guardianExtension = project.getExtensions()
          .getByType(GuardianExtension.class);

      configurePlugins(evaluatedProject);
      configureDependencies(evaluatedProject, guardianExtension);
      configureGitHooks(evaluatedProject, guardianExtension);

      boolean enableErrorProne = guardianExtension.getErrorProne().isEnable();
      if (enableErrorProne) {
        configureErrorProne(evaluatedProject, guardianExtension);
      }

      boolean enableChecker = guardianExtension.getCheckStyle().isEnable();
      if (enableChecker) {
        project.getPlugins().apply("checkstyle");
        configureValidateStyleTask(evaluatedProject);
        configureDownloadConfigFileTask(evaluatedProject, guardianExtension);
        configureCheckstyle(project, guardianExtension);
      }
    });
  }

  private void configurePlugins(Project project) {
    Set<String> plugins = Set.of("java", "net.ltgt.errorprone");

    plugins.stream()
        .filter(plugin -> !project.getPlugins().hasPlugin(plugin))
        .forEach(plugin -> project.getPlugins().apply(plugin));
  }

  private void configureDependencies(Project project, GuardianExtension guardianExtension) {
    ErrorProneExtension errorProne = guardianExtension.getErrorProne();
    String dependency = errorProne.getDependency();
    String dependencyJavac = errorProne.getDependencyJavac();

    project.getDependencies().add("errorprone", dependency);
    project.getDependencies().add("errorproneJavac", dependencyJavac);
  }

  private void configureDownloadConfigFileTask(Project project, GuardianExtension guardianExtension) {
    GuardianCheckStyleExtension checkStyleExtension = guardianExtension.getCheckStyle();
    String fileUrl = checkStyleExtension.getFileUrl();

    if (fileUrl == null) {
      return;
    }

    String filePath = getCustomCheckStyleFilePath(project);
    project.getTasks().register(TaskName.DOWNLOAD_CHECKSTYLE_FILE, DownloadCheckstyleFile.class,
        task -> {
          task.setGroup("verification");
          configureDownloadConfigFileProperties(task, guardianExtension, filePath);
          task.dependsOn(CleanRule.CLEAN);
        });

    project.getTasks().named(TaskName.VALIDATE_STYLE)
        .configure(task -> task.dependsOn(TaskName.DOWNLOAD_CHECKSTYLE_FILE));

    project.getTasks().withType(Delete.class, cleanTask -> {
      cleanTask.delete(project.getLayout().getProjectDirectory().file(filePath));
    });
  }

  private void configureDownloadConfigFileProperties(
      DownloadCheckstyleFile task,
      GuardianExtension guardianExtension,
      String filePath
  ) {
    GuardianCheckStyleExtension checkStyleExtension = guardianExtension.getCheckStyle();
    String checkstyleFileUrl = checkStyleExtension.getFileUrl();

    boolean isPresentUrl = task.getUrl().isPresent();
    boolean isPresentDestinationPath = task.getDestinationPath().isPresent();

    if (!isPresentUrl) {
      task.getUrl().convention(checkstyleFileUrl);
    }

    if (!isPresentDestinationPath) {
      task.getDestinationPath().convention(filePath);
    }
  }

  private void configureValidateStyleTask(Project project) {
    project.getTasks().register(TaskName.VALIDATE_STYLE, JavaCompile.class, task -> {
      task.setGroup("verification");
      task.setDescription("Validates Java code against coding style rules");
    });

    project.getTasks().named("build").configure(task -> task.dependsOn(TaskName.VALIDATE_STYLE));
    project.getTasks().named("checkstyleMain").configure(task -> task.dependsOn(TaskName.VALIDATE_STYLE));
  }

  private void configureCheckstyle(Project project, GuardianExtension guardianExtension) {
    GuardianCheckStyleExtension guardianCheckStyleExtension = guardianExtension.getCheckStyle();

    CheckstyleExtension checkstyleExtension = project.getExtensions()
        .findByType(CheckstyleExtension.class);

    if (checkstyleExtension != null) {
      configureCheckStyleExtension(project, checkstyleExtension, guardianCheckStyleExtension);
    }

    // it's bug in a checkstyle plugin https://github.com/gradle/gradle/issues/27035#issuecomment-1814589243
    Configuration checkstyleConfig = project.getConfigurations().getByName("checkstyle");
    checkstyleConfig.getResolutionStrategy().capabilitiesResolution(capabilitiesResolution -> {
      capabilitiesResolution.withCapability("com.google.collections:google-collections",
          details -> details.select("com.google.guava:guava:0"));
    });

    project.getTasks().withType(Checkstyle.class).configureEach(checkstyle -> {
      checkstyle.reports(reports -> {
        reports.getHtml().getRequired().convention(true);
      });
    });
  }

  private void configureCheckStyleExtension(
      Project project,
      CheckstyleExtension checkstyleExtension,
      GuardianCheckStyleExtension guardianCheckStyleExtension
  ) {
    String fileUrl = guardianCheckStyleExtension.getFileUrl();
    String filePath = fileUrl == null ? getGuardianCheckStyleFilePath(project) : getCustomCheckStyleFilePath(project);

    int errorThreshold = guardianCheckStyleExtension.getErrorThreshold();
    boolean showViolations = guardianCheckStyleExtension.isShowViolations();
    String version = guardianCheckStyleExtension.getVersion();
    boolean includeTest = guardianCheckStyleExtension.isIncludeTest();

    checkstyleExtension.setToolVersion(version);
    checkstyleExtension.setMaxErrors(errorThreshold);
    checkstyleExtension.setShowViolations(showViolations);
    checkstyleExtension.setMaxWarnings(0);
    checkstyleExtension.setIgnoreFailures(false);
    checkstyleExtension.setConfigFile(new File(filePath));

    SourceSetContainer sourceSetContainer = (SourceSetContainer) project.getProperties().get("sourceSets");
    ArrayList<SourceSet> sourceSets = new ArrayList<>();
    sourceSets.add(sourceSetContainer.getByName("main"));
    if (includeTest) {
      sourceSets.add(sourceSetContainer.getByName("test"));
    }

    checkstyleExtension.setSourceSets(sourceSets);
  }

  private String getGuardianCheckStyleFilePath(Project project) {
    URL url = getClass().getClassLoader().getResource("guardian-checkstyle.xml");
    if (url != null) {
      return project.getResources().getText().fromUri(url).asFile().getPath();
    }

    return null;
  }

  private String getCustomCheckStyleFilePath(Project project) {
    return project.getRootProject().getRootDir().getAbsolutePath() + "/config/checkstyle/checkstyle.xml";
  }

  private void configureErrorProne(Project project, GuardianExtension guardianExtension) {
    project.getTasks().withType(JavaCompile.class).configureEach(javaCompile -> {
      ErrorProneCompilerArgumentProvider options = (ErrorProneCompilerArgumentProvider) javaCompile.getOptions()
          .getCompilerArgumentProviders().stream()
          .filter(ErrorProneCompilerArgumentProvider.class::isInstance)
          .findAny()
          .get();

      ErrorProneOptions errorproneOptions = options.getErrorproneOptions();
      if (errorproneOptions != null) {
        configureErrorProneOptions(errorproneOptions, guardianExtension);
      }
    });
  }

  private void configureErrorProneOptions(
      ErrorProneOptions options,
      GuardianExtension guardianExtension
  ) {
    ErrorProneExtension errorProne = guardianExtension.getErrorProne();

    options.getEnabled().convention(true);
    options.getDisableAllWarnings().convention(true);
    options.getDisableWarningsInGeneratedCode().convention(true);
    options.getExcludedPaths().convention(".*/build/generated/.*");
    options.getAllErrorsAsWarnings().convention(false);

    errorProne.getBugPatterns().forEach(bugPatter -> options.check(bugPatter, CheckSeverity.ERROR));
  }

  private void configureGitHooks(Project project, GuardianExtension guardianExtension) {
    boolean enableGitHooks = guardianExtension.isEnableGitHooks();

    if (!enableGitHooks) {
      return;
    }

    String destDir = project.getRootProject().getRootDir().getAbsolutePath() + "/.git/hooks";
    String fileName = "pre-push";
    project.getTasks().register(TaskName.COPY_GIT_HOOKS, Copy.class, copyTask -> {
      copyTask.setGroup("help");
      copyTask.setDescription("Add pre-push script, which will try build a gradle project  before push to repository");

      URL prePushHookUrl = getClass().getClassLoader().getResource("git-hooks/pre-push.sh");
      if (prePushHookUrl != null) {
        copyTask.from(project.getResources().getText().fromUri(prePushHookUrl), copySpec -> {
          copySpec.rename(uriName -> fileName);
          copySpec.setFileMode(Integer.valueOf("777", 8)); // https://github.com/gradle/gradle/issues/21171
        });
      }

      copyTask.into(destDir);
      copyTask.setDuplicatesStrategy(DuplicatesStrategy.INCLUDE);
    });

    Task copyGitHooksTask = project.getTasks().findByName(TaskName.COPY_GIT_HOOKS);

    project.getTasks().named("build").configure(buildTask ->
        buildTask.dependsOn(copyGitHooksTask)
    );

    project.getTasks().withType(Delete.class, cleanTask -> {
      cleanTask.delete(project.getLayout().getProjectDirectory().file(String.format("%s/%s", destDir, fileName)));
    });
  }
}
