# commons-i18n-starter

## 1. I18n国际化

### 1) 添加依赖

```
	<dependency>
		<groupId>org.micro</groupId>
		<artifactId>common-i18n-starter</artifactId>
		<version>${common.version}</version>
	</dependency>

```

### 2) 修改application.yml配置

```
# i18n
i18n:
  config:
    enabled: true
    
```

### 3) 在classpath下添加i18n/messages_*.properties

```
英文：messages_en_US.properties
中文：messages_zh_CN.properties

```

### 4) 配置Controller所在package

```
@EnableI18n
@ControllerAdvice("xxx.xxx.template.controller")
public class I18nConfig extends I18nResponseAdvice {

}

```

### 5) 封装Result对象

例：

```
datahub.repository.MSGE000001=Save Job Error: The job is out of date cron=%s, effective time %s ~ %s
datahub.repository.MSGE000001=任务保存错误: 任务已过时cron=%s, 有效时间 %s ~ %s

@GetMapping("/msg")
public Result getMsg() {
	Result res = new Result();
	
	I18nModel i18n = new I18nModel(MSGE000001);
	String args[] = {"0 /1 * * * ?", "2018-10-20 00:00:00", "2050-12-31 23:59:59"};
	i18n.setArgs(args);
	
	res.setModel(i18n);
	return res;
}

```

### 6) 请求header中添加参数lang=en_US或zh_CN, 取值和properties文件后缀名一致

例：lang=zh_CN

```
http://localhost:8081/msg
{
  "code": 0,
  "msg": "任务保存错误: 任务已过时cron=0 /1 * * * ?, 有效时间 2018-10-20 00:00:00 ~ 2050-12-31 23:59:59",
  "model": {
    "title": null,
    "detail": null,
    "key": "datahub.repository.MSGE000001",
    "args": [
      "0 /1 * * * ?",
      "2018-10-20 00:00:00",
      "2050-12-31 23:59:59"
    ]
  }
}

```