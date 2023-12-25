# CodeGuardian

CodeGuardian is a Java-based project focusing on improving code quality and security. This project provides tools and methods to enhance coding practices, ensuring better maintainability and security standards in Java applications.

## Features

- **ErrorProne** Preconfigured: Integrates ErrorProne, a tool for catching common programming mistakes in Java. This feature is preconfigured to provide out-of-the-box utility.

- **Git Hooks** on Push: Implements Git hooks that activate on 'push' operations, ensuring run gradle test and gradle build before git push

```bash
git stash -q --keep-index
./gradlew test
./gradlew clean build -x test
status=$?
git stash pop -q
exit $status
```

- **Checkstyle** Preconfigured: Comes with a preconfigured Checkstyle setup, a tool for enforcing coding standards and styles in Java code.
   This ensures consistency and adherence to best coding practices across the project. By default it use google style code with additional checks. It's guardian code style.
   You can see it [here](plugin/src/main/resources/guardian-checkstyle.xml)

## Getting Started

### Prerequisites

- Java JDK (version 11 or newer)
- Gradle for build automation

### Installation and Usage

```gradle
id 'io.github.Anth3995.guardian' version '1.0.0'
```

## Configuration (gradle DLS) (it shows default configuration)
```gradle    
guardian {
  enableGitHooks = true // Enables or disables Git hooks integration.

  errorProne {
    enable = true // Enables or disables ErrorProne.
    dependency = 'com.google.errorprone:error_prone_core:2.23.0' // Specifies the dependency (errorprone) for ErrorProne
    dependencyJavac = 'com.google.errorprone:javac:9+181-r4173-1' // Specifies the dependency (errorproneJavac) for ErrorProne javac.
    bugPatterns = [] // // A list to specify custom bug patterns for ErrorProne to detect.
  } 

  checkStyle {
    enable = true // Enables or disables CheckStyle.
    fileUrl = null // URL to a custom CheckStyle configuration file (from S3 for example); the default is using [link]
    errorThreshold = 0 // Threshold for the number of errors to tolerate before failing the build.
    showViolations = true // Whether to display the details of style violations.
    version = '10.12.6' // Specifies the version of CheckStyle to use.
  }
}

```

bugPatterns = [link](https://github.com/Anth3995/CodeGuardian/blob/35d0f125ec5b8dc1a9e36ad9b7f3f2923180de6d/plugin/src/main/java/io/github/Anth3995/guardian/extention/ErrorProneExtension.java#L13-L139)

[default checkStyle](https://github.com/Anth3995/CodeGuardian/blob/master/plugin/src/main/resources/guardian-checkstyle.xml)

## Contributing

Contributions are welcome.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

CodeGuardian © 2023 by Anth3995
