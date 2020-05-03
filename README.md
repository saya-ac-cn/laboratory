# laboratory
实验室项目单体应用
部署打包 mvn clean package -Dmaven.test.skip=true

> ## 2019-08-10 修改记录-重大修改
* 增加事务（以xml配置为主，亦支持注解式）
* 引入包spring-boot-starter-aop
* 配置xml文件
* 引入到启动文件
* 注解式只需要加入@Transactional(propagation= Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor=Exception.class,readOnly = false)

> ## 2020-01-05 修改记录-重大修改
* spring boot嵌入jetty服务容器
* 更改项目打包方式，配置及日志全部打包到外部
* 启动方式：java -Xbootclasspath/a:./ -jar laboratory.jar
* Spring Boot加载核心配置文件过程见：https://www.jianshu.com/p/c7a93a3fde9a

> ## 2020-05-02 修改记录-重大修改
* 修改项目为多数据源
* 增加异步数据库备份
* 财政金融数据不能修改过往历史
* 项目事务切换为注解方式，通过业务解决全局事务


## 修改记录
