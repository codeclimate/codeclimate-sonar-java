# Code Climate Sonar-Java Engine

[![Maintainability](https://api.codeclimate.com/v1/badges/1ad407dbd79378cf4b07/maintainability)](https://codeclimate.com/repos/59e0f09e141c6104f9000002/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/1ad407dbd79378cf4b07/test_coverage)](https://codeclimate.com/repos/59e0f09e141c6104f9000002/test_coverage)
[![CircleCI](https://circleci.com/gh/codeclimate/codeclimate-sonar-java.svg?style=svg&circle-token=b800791f4e3af9079991ef70f3871f0ce09ccc81)](https://circleci.com/gh/codeclimate/codeclimate-sonar-java)

`codeclimate-sonar-java` is a Code Climate engine that wraps [Sonarlint](http://www.sonarlint.org) in standalone mode.

## Installation
```
make image
```

## Tests
```
make test
```

## Usage

1. If you haven't already, [install the Code Climate CLI](https://github.com/codeclimate/codeclimate).
2. Configure a `.codeclimate.yml` file in your repo.
```yml
engines:
  sonar-java:
    enabled: true
    config:
      sonar.java.source: 7
      tests_patterns:
        - src/test/**
exclude_paths:
  - build/
```
3. Run `codeclimate analyze`.

## Custom configurations

### Java source version
It is possible to specifcy a Java version the code should be compliant to, it helps Sonar to use the proper rules.
```
engines:
  sonar-java:
    enabled: true
    config:
      sonar.java.source: 7
```

### Tests
Specifying where the test classes are helps Sonar to use specific rules for those files.
```
engines:
  sonar-java:
    enabled: true
    config:
      tests_patterns:
        - src/test/**
        - app/src/test/**
```

### Severity
Ignore issues with severity below the minimum:
```
engines:
  sonar-java:
    enabled: true
    config:
      minimum_severity: critical  # default: major
                                  # valid values are: info, minor, major, critical, blocker
```

## Sonar Documentation

http://www.sonarlint.org/commandline

http://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner

Issue Tracker: http://jira.sonarsource.com/browse/SLCLI

## Copyright

This engine is developed by Code Climate using [SonarLint](http://www.sonarlint.org/commandline), it is not endorsed by SonarSoruce.

See [LICENSE](LICENSE)
test
