# 去掉 druid-monitor 广告

> 以 `druid-1.1.16.jar` 版本为例

* 1.找到 `druid-1.1.16.jar`
```bash
$ cd /home/tomoncle/.m2/repository/com/alibaba/druid/1.1.16
```

* 2.从jar包中查找`support/http/resources/js/common.js`文件
```bash
$ jar tf druid-1.1.16.jar | grep support/http/resources/js/common.js
```

* 3.从jar包中拷贝出`support/http/resources/js/common.js`文件
```bash
$ jar xf druid-1.1.16.jar support/http/resources/js/common.js
```

* 4.编辑拷贝出的`support/http/resources/js/common.js`文件, 将`buildFooter`函数修改如下：
```html
<!-- vim support/http/resources/js/common.js -->

                buildFooter : function() {

                        var html ='<footer class="footer">'+
                                          ' </footer>';
                        $(document.body).append(html);
                },

```

* 5.将编辑好的文件覆盖到jar包
```bash
$ jar uf druid-1.1.16.jar support/http/resources/js/common.js
```

* 6.重新构建打包项目即可. 广告消失


# Jar 命令

* 列出jar包中的文件清单
```bash
$ jar tf test.jar
```

* 提取出内部jar包的指定文件
```bash
$ jar xf test.jar support/http/resources/index.html
```

* 更新配置文件到内部jar包.(存在覆盖，不存在就新增)
```bash
$ jar uf test.jar support/http/resources/index.html
```

* 直接编辑jar包
```bash
$ vim test.jar
# 1. 使用 /{fileName} 命令 找到文件 按 Enter, 直接编辑, 完成之后 :wq 退出当前编辑的文件
# 2. :q  退出jar包
```