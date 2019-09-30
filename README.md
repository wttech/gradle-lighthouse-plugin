[![Cognifide logo](docs/cognifide-logo.png)](http://cognifide.com)

[![Gradle Status](https://gradleupdate.appspot.com/Cognifide/gradle-lighthouse-plugin/status.svg?random=123)](https://gradleupdate.appspot.com/Cognifide/gradle-lighthouse-plugin/status)
![Travis Build](https://travis-ci.org/Cognifide/gradle-lighthouse-plugin.svg?branch=master)
[![Apache License, Version 2.0, January 2004](docs/apache-license-badge.svg)](http://www.apache.org/licenses/)

# Gradle Lighthouse Plugin

Runs [Lighthouse](https://developers.google.com/web/tools/lighthouse) tests on multiple sites / web pages with checking thresholds (useful on continuous integration, constant performance checking).

[![Lighthouse Logo](docs/lighthouse-logo.png)](https://developers.google.com/web/tools/lighthouse)

## Configuration

Plugin organizes multiple sites to be tested into suites. Any web page could have be checked with different threshold and Lighthouse configuration (e.g using [performance budgets](https://developers.google.com/web/tools/lighthouse/audits/budgets)).
Suites need to be defined in file with following format:

*lighthouse/suites.json* (plugin specific format)
```json
{
  "suites": [
    {
      "name": "site.demo",
      "baseUrl": "http://demo.example.com",
      "paths": [
        "/en-us.html",
        "/en-gb.html"
      ],
      "args": [
        "--config-path=lighthouse/config.json",
        "--performance=90",
        "--accessibility=90",
        "--best-practices=80",
        "--seo=60",
        "--pwa=30"
      ]
    },
    {
      "name": "site.live",
      "baseUrl": "http://example.com",
      "paths": [
        "/en-us.html"
      ],
      "args": [
        "--config-path=lighthouse/config.json",
        "--performance=90",
        "--accessibility=90",
        "--best-practices=80",
        "--seo=60",
        "--pwa=30"
      ]
    }
  ]
}
```

When using argument `--config-path` then it is also needed to have at least file:

*lighthouse/config.json* ([documentation](https://github.com/GoogleChrome/lighthouse/blob/master/docs/configuration.md#config-extension))
```json
{
  "extends": "lighthouse:default"
}
```

### Running

Use command, to run Lighthouse CI by using:

* default suite and its base URL: `sh gradlew lighthouseRun`,
* only desired suite by name: `sh gradlew lighthouseRun -Plighthouse.suite=site.demo`,
* only desired suite by base URL: `sh gradlew lighthouseRun -Plighthouse.baseUrl=http://example.com`,
* any suite with any base URL: `sh gradlew lighthouseRun -Plighthouse.baseUrl=http://any-host.com -Plighthouse.suite=site.live` (base URL defined in suite will be overridden).

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
