# laboratory
实验室项目单体应用

## 2019-08-10 修改记录-重大修改
* 增加事务（已xml配置为主，亦支持注解式）
* 引入包spring-boot-starter-aop
* 配置xml文件
* 引入到启动文件
* 注解式只需要加入@Transactional(propagation= Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor=Exception.class,readOnly = false)

## 修改记录
