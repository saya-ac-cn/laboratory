FROM registry.cn-hangzhou.aliyuncs.com/pandoras/base-sdk:jdk11.0.10

ENV JAVA_OPTS="-Xbootclasspath/a:./ -Xms128m -Xmx256m -Xss256K -XX:MetaspaceSize=32m -XX:MaxMetaspaceSize=64m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Djava.awt.headless=true -Duser.timezone=Asia/Shanghai"

#启动项目的名称
ENV APP_NAME=laboratory
#启动JAR包
ENV APP_JAR=laboratory.jar
#需要启动的Java主程序（main方法类）
ENV APP_MAINCLASS=ac.cn.saya.laboratory.LaboratoryApplication

ENV APP_HOME=/opt/server
ENV APP_LOGS_HOME=/opt/server/logs

#拼凑完整的classpath参数，包括指定lib目录下所有的jar
ENV CLASSPATH=.:$APP_HOME/$APP_NAME/$APP_JAR
#ENV CLASSPATH=$CLASSPATH:$APP_HOME/$APP_JAR:$APP_HOME/conf/:$APP_HOME/lib

RUN mkdir -p $APP_LOGS_HOME \
    chmod 777 $APP_LOGS_HOME

##add files to images tomcat webapps
ADD ./laboratory.tar.gz $APP_HOME/


EXPOSE 8080

WORKDIR $APP_HOME/$APP_NAME

ENV TZ=Asia/Shanghai LANG="C.UTF-8"
# $JAVA_CMD $JAVA_OPTS  -classpath  $CLASSPATH  $APP_MAINCLASS start >> ${APP_LOGS_HOME}/init.log
##注意-Dcatalina.home 这个参数必须 与log4j.properties 中的应用对应起来
CMD java ${JAVA_OPTS} -classpath ${CLASSPATH} ${APP_MAINCLASS} start >> ${APP_LOGS_HOME}/init.log