# CodeGuardian

CodeGuardian is a Java-based project focusing on improving code quality and security. This project provides tools and methods to enhance coding practices, ensuring better maintainability and security standards in Java applications.

## Features

- **ErrorProne** Preconfigured: Integrates ErrorProne, a tool for catching common programming mistakes in Java. This feature is preconfigured to provide out-of-the-box utility.

- **Git Hooks** on Push: Implements Git hooks that activate on 'push' operations, ensuring run gradle test and gradle build before git push

```bash
git stash -q --keep-index
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

- Java JDK 17+
- Gradle 5.0+

### Installation and Usage

```gradle
plugins {
    id 'io.github.alexeytereshchenko.guardian' version '1.2.1'
}
```

## Configuration (gradle DLS) (it shows default configuration)
```gradle    
guardian {
  enableGitHooks = true // Enables or disables Git hooks integration.

  errorProne {
    enable = true // Enables or disables ErrorProne.
    dependency = 'com.google.errorprone:error_prone_core:2.23.0' // Specifies the dependency (errorprone) for ErrorProne
    dependencyJavac = 'com.google.errorprone:javac:9+181-r4173-1' // Specifies the dependency (errorproneJavac) for ErrorProne javac.
    bugPatterns = [] // A list to specify custom bug patterns for ErrorProne to detect. See link below to view default patters
  } 

  checkStyle {
    enable = true // Enables or disables CheckStyle.
    fileUrl = null // URL to a custom CheckStyle configuration file (from S3 for example). See link below to view default
    errorThreshold = 0 // Threshold for the number of errors to tolerate before failing the build.
    showViolations = true // Whether to display the details of style violations.
    version = '10.12.6' // Specifies the version of CheckStyle to use.
  }
}

```

[bugPatterns](https://github.com/alexeytereshchenko/CodeGuardian/blob/d27833940934fe3fd59b02cff0f792aeb8982625/plugin/src/main/java/io/github/oleksiitereshchenko/guardian/extention/ErrorProneExtension.java#L14-L137)

[default checkStyle](plugin/src/main/resources/guardian-checkstyle.xml)

## Contributing

Contributions are welcome.

## It's board of project

Trello [project board](https://trello.com/b/h9S0f27p/guardian)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

CodeGuardian © 2023 by Alexey Tereshchenko
