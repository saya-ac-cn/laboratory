# laboratory
实验室项目单体应用
部署打包 mvn clean package -Dmaven.test.skip=true

## 2019-08-10 修改记录-重大修改
* 增加事务（已xml配置为主，亦支持注解式）
* 引入包spring-boot-starter-aop
* 配置xml文件
* 引入到启动文件
* 注解式只需要加入@Transactional(propagation= Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor=Exception.class,readOnly = false)

## 2020-01-05 修改记录-重大修改
* spring boot嵌入jetty服务容器
* 更改项目打包方式，配置及日志全部打包到外部
* 启动方式：java -Xbootclasspath/a:./ -jar laboratory.jar
* Spring Boot加载核心配置文件过程见：https://www.jianshu.com/p/c7a93a3fde9a

## 修改记录
