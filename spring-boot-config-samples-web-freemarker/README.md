# tomcat-war-*
> 项目打包为`war`包使用外部`tomcat`启动项目

# 关于首页的默认显示

### war

`spingboot` 使用外部 `tomcat` 启动时，会默认加载 `webapp` 下的 `index.jsp`或 `index.html`；
假如找到该文件，则显示该文件；
假如没有找到会根据 `controller` 配置的 `/` 根路径及配置文件中配置的页面路径地址，联合查找回调的页面位置

* 配置文件 `resources:application.properties`

```properties
### war package config
spring.freemarker.template-loader-path=/WEB-INF/templates/
spring.freemarker.suffix=.ftl
spring.freemarker.enabled=true
```

### jar

`spingboot` 使用内置 `tomcat` 启动时，会根据 `controller` 配置的 `/` 根路径及配置文件中配置的页面路径地址，
联合查找回调的页面位置

* 配置文件 `resources:application.properties`

```properties
### jar package config
spring.freemarker.template-loader-path=classpath:/static/templates/
spring.freemarker.suffix=.ftl
spring.freemarker.enabled=true
```
