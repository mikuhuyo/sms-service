# 短信服务使用手册

[![GitHub license](https://img.shields.io/github/license/mikuhuyo/sms-service)](https://github.com/mikuhuyo/sms-service/blob/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/mikuhuyo/sms-service)](https://github.com/mikuhuyo/sms-service/issues)
[![GitHub stars](https://img.shields.io/github/stars/mikuhuyo/sms-service)](https://github.com/mikuhuyo/sms-service/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/mikuhuyo/sms-service)](https://github.com/mikuhuyo/sms-service/network)
![Java version](https://img.shields.io/badge/Jdk-11-yellow)
![SpringBoot version](https://img.shields.io/badge/SpringBoot-2.3.12.RELEASE-brightgreen)

## 修改redis相关配置

将下面`redis`相关配置修改为你的`redis`相关配置:

```yaml
spring:
  cache:
    type: REDIS
  redis:
    host: 127.0.0.1
    password: yueliminvc@outlook.com
    port: 6379
    database: 0
    timeout: 3000ms
    lettuce:
      pool:
        max-idle: 8
        min-idle: 1
        max-active: 8
        max-wait: 30000ms
      shutdown-timeout: 10000ms
```

## 修改腾讯云相关配置

将`application.yml`文件中腾讯云配置部分修改为你的配置:

```yaml
sms:
  defaultExpire: 300
  qcloud:
    # your app id
    appId: 000000
    appKey: 'your_app_key'
    templateId: 'your_sms_template'
    sign: 'your_tencent_cloud_sms_sign'
    # tencent sms api
    url: 'https://yun.tim.qq.com/v5/tlssmssvr/sendsms'
```

## 接口请求

> http://127.0.0.1:52000/swagger-ui.html

> 获取验证码 http://127.0.0.1:52000/sms/tencent/generat/{effectiveTime}

参数`effectiveTime`: 过期时间, 单位秒, 必选项

请求体:

```json
{
  "mobile": "15711111111"
}
```

返回值:

```json
{
  "code": 0,
  "msg": "success",
  "result": {
    "key": "tencent:sms:?"
  }
}
```

> 验证码校验 http://127.0.0.1:52000/sms/tencent/verification/{key}/{code}

参数`key`: 生成的签名

参数`code`: 生成的验证码

返回值:

```json
{
  "code": 0,
  "msg": "success",
  "result": true // true表示成功, false表示失败
}
```

## 特别鸣谢

### 关注者

[![Stargazers repo roster for @mikuhuyo/sms-service](https://reporoster.com/stars/mikuhuyo/sms-service)](https://github.com/mikuhuyo/sms-service/stargazers)

### 收藏者

[![Forkers repo roster for @mikuhuyo/sms-service](https://reporoster.com/forks/mikuhuyo/sms-service)](https://github.com/mikuhuyo/sms-service/network/members)

## 请这个b喝杯水?

![Alipay](./image/alipays.png)

---

![WeChatPay](./image/wechats.png)