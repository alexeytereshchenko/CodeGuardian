package io.github.Anth3995.guardian.extention;

import org.gradle.api.Action;
import org.gradle.api.plugins.ExtensionAware;

public abstract class GuardianExtension implements ExtensionAware {
  private final ErrorProneExtension errorProne = new ErrorProneExtension();
  private final GuardianCheckStyleExtension checkStyle = new GuardianCheckStyleExtension();
  private boolean enableGitHooks = true;

  public boolean isEnableGitHooks() {
    return enableGitHooks;
  }

  public void setEnableGitHooks(boolean enableGitHooks) {
    this.enableGitHooks = enableGitHooks;
  }

  public ErrorProneExtension getErrorProne() {
    return errorProne;
  }

  public GuardianCheckStyleExtension getCheckStyle() {
    return checkStyle;
  }

  public void errorProne(Action<? super ErrorProneExtension> action) {
    action.execute(errorProne);
  }

  public void checkStyle(Action<? super GuardianCheckStyleExtension> action) {
    action.execute(checkStyle);
  }
}
