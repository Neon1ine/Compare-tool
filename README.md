# Compare tool

### Hexlet tests and linter status:
[![Actions Status](https://github.com/Neon1ine/java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/Neon1ine/java-project-71/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/17ca376b3163a2e29f24/maintainability)](https://codeclimate.com/github/Neon1ine/java-project-71/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/17ca376b3163a2e29f24/test_coverage)](https://codeclimate.com/github/Neon1ine/java-project-71/test_coverage)

## description:
Compares two configuration files and shows a difference.

### Options:
* -f, --format=format   output format [default: stylish]
* -h, --help      Show this help message and exit.
* -V, --version   Print version information and exit.

### Available formats:
* *.json
* *.yml
* *.yaml

### Available make commands:
* run-dist:
	./build/install/app/bin/app
* setup:
	./gradlew wrapper
* clean:
	./gradlew clean
* run:
	./gradlew run
* install:
	./gradlew clean install
* build:
	./gradlew clean build --stacktrace
* report:
	./gradlew jacocoTestReport
* test:
	./gradlew test
* checkStyle:
	./gradlew checkstyleMain
  
## examples:
### latest build

[![asciicast](https://asciinema.org/a/664365.svg)](https://asciinema.org/a/664365)

### old build

files difference with json output (step 10)
[![asciicast](https://asciinema.org/a/662662.svg)](https://asciinema.org/a/662662)

files difference with plain output (step 9)
[![asciicast](https://asciinema.org/a/662587.svg)](https://asciinema.org/a/662587)

complicated json files difference with stylish output (step 8)
[![asciicast](https://asciinema.org/a/662578.svg)](https://asciinema.org/a/662578)

yml files difference (step 7)
[![asciicast](https://asciinema.org/a/662508.svg)](https://asciinema.org/a/662508)

json files difference (step 5)
[![asciicast](https://asciinema.org/a/4eAInCYs2m8jZmEbB723mUlP2.svg)](https://asciinema.org/a/4eAInCYs2m8jZmEbB723mUlP2)
