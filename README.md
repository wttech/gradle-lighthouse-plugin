[![Cognifide logo](docs/cognifide-logo.png)](http://cognifide.com)

[![Gradle Status](https://gradleupdate.appspot.com/Cognifide/gradle-lighthouse-plugin/status.svg?random=123)](https://gradleupdate.appspot.com/Cognifide/gradle-lighthouse-plugin/status)
![Travis Build](https://travis-ci.org/Cognifide/gradle-lighthouse-plugin.svg?branch=master)
[![Apache License, Version 2.0, January 2004](docs/apache-license-badge.svg)](http://www.apache.org/licenses/)

# Gradle Lighthouse Plugin

TODO ...

## Building

1. Clone this project using command `git clone https://github.com/Cognifide/gradle-lighthouse-plugin.git`
2. To build plugin, simply enter cloned directory run command: `gradlew`
3. To debug built plugin:
    * Append to build command parameters `--no-daemon -Dorg.gradle.debug=true`
    * Run build, it will suspend, then connect remote at port 5005 by using IDE
    * Build will proceed and stop at previously set up breakpoint.

## Contributing

Issues reported or pull requests created will be very appreciated. 

1. Fork plugin source code using a dedicated GitHub button.
2. Do code changes on a feature branch created from *develop* branch.
3. Create a pull request with a base of *develop* branch.

## License

**Gradle AEM Plugin** is licensed under the [Apache License, Version 2.0 (the "License")](https://www.apache.org/licenses/LICENSE-2.0.txt)
