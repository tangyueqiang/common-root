# commons-upgrade-starter

## 日志采集客户端

### 1. 添加依赖

```
	<dependency>
		<groupId>com.gouuse</groupId>
		<artifactId>commons-upgrade-starter</artifactId>
		<version>${commons.version}</version>
	</dependency>
```

### 2. 添加注解@EnableLogging

```
	@EnableLogging
	@SpringBootApplication
	public class Application{
	}

```

### 3. 修改配置

bootstrap.yml中加入以下配置

```
# 说明：配置过滤器类型，被过滤器拦截到的日志将通过Appender适配发送到响应的位置
# 不发送（默认配置）：Default
# ES适配，环境配置中需要配置ES连接信息：Elasticsearch
# RabbitMQ适配，环境配置中需要配置RabbitMQ连接信息：Rabbitmq

# logging
logging:
  path: /edp/logs/${spring.application.name}
  level:
    root: info
  edp:
    filter:
      level: error
      type: rabbitmq
      resource-pool: edp-pool
      tenant-name: EDP系统
```