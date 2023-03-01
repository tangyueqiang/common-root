# commons-upgrade-starter
### 微服务版本自动升级程序，适用于spring-boot项目

#### 1. 添加依赖
```
<dependency>
	<groupId>org.micro</groupId>
	<artifactId>common-upgrade-starter</artifactId>
	<version>${common.version}</version>
</dependency>

```

#### 2. 添加注解
```
@EnableUpgrade
public class Application

```

#### 3. 修改配置
服务启动自动执行升级脚本，如果该版本已经升级，会跳过升级脚本
```
upgrade:
  enabled: true
  version: v3.0.0
  application-name: ${spring.application.name}
  upgrade-script: classpath:/upgrade/connector_repository_v2.0.0-v3.0.0.sql

```

#### 4. 需要升级的场景

需要准备两个脚本：

- 全新安装新版本脚本：用于第一次全新安装，如connector_repository_v3.0.0.sql，里面包含新版本修改和历史全部脚本；

- 基于上一个版本的升级脚本：用于基于第上一个版本升级，如connector_repository_v2.0.0-v3.0.0.sql，只有新版本的升级脚本；

  

  *`注意：升级一定切记指定升级脚本文件，指定错误会全部初始化`*