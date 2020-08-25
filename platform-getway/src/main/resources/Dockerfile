FROM registry.cn-hangzhou.aliyuncs.com/wwj_dev/jdk1.8:1.0

MAINTAINER JAVER <wejam>

# 1. JVM配置： docker-compose.yml中根据具体环境需改配置
ENV JVM_OPTS -Xmx1344M -Xms1344M -XX:MaxMetaspaceSize=256M -XX:MetaspaceSize=256M -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ParallelRefProcEnabled
# 2. 程序运行环境： docker-compose.yml中根据具体环境修改配置  dev|test1|test2|uat|prod
ENV PROFILES_ACTIVE dev
# 3. nacos服务配置文件地址： docker-compose.yml中根据具体环境需改配置
ENV SERVER_ADDR 127.0.0.1:8848
## 3.1 如果nacos启动认证,将认证信息作为环境变量
ENV NACOS_USERNAME nacos
ENV NACOS_PASSWORD nacos
# 4. skywalking 配置信息：
## 4.1. agent.service_name： 服务名(在SkywalkingUI 上展示)
## 4.2. agent.sample_n_per_3_secs：每3秒的采样数， -1代表全部
## 4.3. collector.backend_service： agent需要同collector进行数据传输的IP和端口
## 4.4. logging.level: agent：记录日志级别
## 4.5. agent.authentication: 与collector进行通信的安全认证，需要同collector中配置相同
ENV SKY_WALKING_AGENT -javaagent:/skywalking/skywalking-agent.jar -Dskywalking.agent.service_name=platform-gateway -Dskywalking.collector.backend_service=127.0.0.1:11800
# -Dskywalking.agent.sample_n_per_3_secs=1
#
# # 5. SKY_WALKING_AGENT配置加载
ADD /target/platform-gateway.jar /platform-gateway.jar

# 6. 服务端口输出
EXPOSE 8000

ENTRYPOINT ["sh", "-c", "java ${JVM_OPTS} ${SKY_WALKING_AGENT} -jar /platform-gateway.jar --spring.cloud.nacos.config.server-addr=${SERVER_ADDR} --spring.cloud.nacos.config.username=${NACOS_USERNAME} --spring.cloud.nacos.config.password=${NACOS_PASSWORD} --spring.profiles.active=${PROFILES_ACTIVE}"]