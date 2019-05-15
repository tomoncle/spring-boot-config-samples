# spring-boot-config-samples

[![Build Status](https://travis-ci.org/tomoncle/spring-boot-config-samples.svg?branch=master)][travis] ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/tomoncle/spring-boot-config-samples.svg) ![GitHub repo size](https://img.shields.io/github/repo-size/tomoncle/spring-boot-config-samples.svg?color=green&logoColor=green) ![GitHub top language](https://img.shields.io/github/languages/top/tomoncle/spring-boot-config-samples.svg?color=yes) ![GitHub issues](https://img.shields.io/github/issues/tomoncle/spring-boot-config-samples.svg) ![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)

spring-boot-config tests and samples.

* running
  * `tomcat-*` is jar run : inherit tomcat container, start with `$ java -jar tomcat-*.jar`
  * `web-*` is war run : Need external `Tomcat` container start project

* default config
  * **jar run** : default resource location is `static`, `public`, `resources`, `/META-INF/resources/`
  * **war run** : you can auto config the location, as `spring.mvc.view.prefix=/WEB-INF/jsp/`
  
  
 
 
 [travis]: https://travis-ci.org/tomoncle/spring-boot-config-samples