## 在线文档
+ [https://www.showdoc.cc/majiangdoc?page_id=2362238912918825](https://www.showdoc.cc/majiangdoc?page_id=2362238912918825)



## 一、准备工作
```
JDK >= 1.8 (推荐1.8版本)
Mysql >= 5.5.0 (推荐5.7版本)
Maven >= 3.0
IntelliJ IDEA 2019.1.2
```
## 二、使用技术
### 2.1 前端
+ `Bootstrap`
+ `Editor.md`
+ `Layer`
+ `Thymeleaf`
### 2.2后台
+ `SpringBoot`
+ `SpringMVC`
+ `Mybatis`
+ `Maven`
+ `Redis`
+ `Github OAuth2`
+ `OKHttp3`
+ `LomBok`
+ `H2 database`
+ `Flayway`
+ `swagger`

## 三、文件结构
```
majiang
├── com.xlx.majiang
│       	└── advice                    // 异常事务
│       	└── cache                     // 常量
│       	└── controller                // 控制层
│       	└── dto                       //数据对象
│       	└── enums                     // 通用枚举
│       	└── exception                 // 通用异常
│       	└── interceptor               // 拦截器
│       	└── utils                     // 通用类处理
│       	└── mapper                    // mybatis generator生成的mapper文件
|
|
├── recesources         // 资源
│       └── db.migration              // Flyway管理的sql脚本
│       └── mapper                    //  mybatis generator生成的XXX.xml数据库操作文件
│       └── static                    // 静态资源
│       └── templates                 // 模板文件
|
|
├── test   // 单元测试
├── MajiangApplication.java      // 项目启动入口
├── application.properties      // 配置文件
|
|-------其他
```

## 核心配置文件
> application.properties
```properties

#开发环境
server.port=8887

#github
github.client.id=
github.client.secret=
github.redirect.uri=http://localhost:8887/callback

#码云
gitee.client.id=
gitee.client.secret=
gitee.redirect.uri=http://localhost:8887/callbackToMY

#发送邮件人
mail.fromMail.addr=
#邮箱服务器地址(QQ)
spring.mail.host=smtp.qq.com
#用户名(发)
spring.mail.username=
spring.mail.password=



#spring-boot默认的HicriCP数据库
spring.datasource.url=jdbc:h2:~/majiang
spring.datasource.username=sa
spring.datasource.password=123
spring.datasource.driver-class-name=org.h2.Driver

# Redis数据库索引（默认为0 redis有16个库）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=127.0.0.1
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.jedis.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.jedis.pool.max-wait=-1ms
# 连接池中的最大空闲连接
spring.redis.jedis.pool.max-idle=8
# 连接池中的最小空闲连接
spring.redis.jedis.pool.min-idle=0
# 连接超时时间（毫秒）
spring.redis.timeout=30000

#thymeleaf
spring.thymeleaf.mode=HTML
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.cache=false

#日志
#logging.file=/log
#logging.level.com.xlx.majiang=info
#logging.level.org.springframework=warn
#logging.level.org.thymeleaf=info
#mybatis
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.type-aliases-package=com.xlx.majiang.system.tag.entity
mybatis.mapper-locations=classpath:mapper/*.xml

```


## 四、 GitHub oauth2认证的过程
### 第一步: 点击第三方登录,认证
  > GET:    https://github.com/login/oauth/authorize?

需要参数 | 描述
:----:|:-----:
`client_id`|github API给定id(String)
`redirect_uri`|认证成功返回的url(String)
`scope`|获取资源信息范围,user用户,repo仓库(String)
`state`|随机字符串(String)


### 第二步: 初次会让你确认是否授权,是,然后会让你登录Github

### 第三步: 登录成功后,会返回你指定的redirect_uri,携带参数code,state

### 第四步: 继续向github请求获取token
  > POST:    https://github.com/login/oauth/access_token

需要参数 | 描述
:----:|:-----:
`client_id`|github API给定id(String)
`client_secret`|github API 给定的secret(String)
`redirect_uri`|认证成功返回的url(String)
`scope`|获取资源信息范围,user用户,repo仓库(String)
`state`|随机字符串(String)
`code`|返回的code(String)

### 第五步: 获取token后,用于请求获取GitHub的用户信息
  > GET:    https://api.github.com/user?

需要参数 | 描述
----|:-----
`access_token`|从github那里获取的token(String)

## 五、表设计
### 5.1  user表/用户
字段|	属性|	描述
:--------:|:----:|:-----
id|	bigint|	主键自增,Long
acount_id|	varchar100|	第三方账号id,String
name|	varchar50|	第三方账号名称,String
avatar_url|	varchar100|	第三方账号头像图片ur,Stringl
token|	varchar36|	密钥,cookie访问,String
gmt_create|	bigint|	创建时间,Long
gmt_modified|	bigint|	修改时间,Long

### 5.2 question表/问题
字段|	属性|	描述
:--------:|:----:|:-----
id|	bigint|	主键自增,Long
title|varchar50|问题标题,String
description|text|问题内容,String
comment_count|int10|评论数量,String
view_count|int10|浏览数量,Integer
like_count|int10|点赞数量,Integer
tag|varchar256|标签,(标签样式),String
creator|bigint|创建/发出问题的人,Long
gmt_create|	bigint|	创建时间,Long
gmt_modified|	bigint|	修改时间,Long

### 5.3 notification表/消息通知
字段|	属性|	描述
:--------:|:----:|:-----
id|	bigint|	主键自增,Long
notifier|bigint|发送通知人id,Long
notifier_name|varchar50|发送通知人的姓名,String`①XXX某某某`
receiver|bigint|接收通知人,Long`回复了谁发的问题,谁发的评论id`
outer_id|	bigint|问题id`问题id,得到title`
type|int10|通知类型,1:回答了问题,2:回复了评论,Integer `②回答了问题/回复了评论`
status|int10|通知状态,默认0:未读,1:已读,Integer`标记问题状态,如未读红色标记`
outer_title|varchar256|问题的标题,String`③问题[为什么离职]`
gmt_create|	bigint|	创建时间,Long
gmt_modified|	bigint|	修改时间,Long


### 5.4comment/评论,提问表
字段|	属性|	描述
:--------:|:----:|:-----
id|	bigint|	主键自增,Long
parent_id|bigint|答问题,parent_id=该问题id,评论答的问题,parent_id=回答的问题的主键id,`只能对某个问题的回答做出一级评论,不能针对个人评论层层递进`
type|int10|类型,Integer`回答该问题1或评论该问题的回答2`
commentator|bigint|用户id,Long`谁回答?,谁评论的?`
comment|varchar1024|回答内容,String`评论内容`
comment_count|int|回复统计,默认0`你发表的多少人回复你`
like_count|int|点赞统计
gmt_create|	bigint|	创建时间,Long
gmt_modified|	bigint|	修改时间,Long


## 六、 项目演示

+ URL: http://localhost:8887/ 
+ 账户1: 41381772 密码:41381772
+ 账户2: 41381773 密码:41381773
+ 账户3: 41381774 密码:41381774
<table>
<tr>
  <td><img src="./display/01.png" title="登录" ></td>
  <td><img src="./display/02.png" title="邮箱验证"></td>
</tr>
<tr>
  <td><img src="./display/03.png" title="首页"></td>
  <td><img src="./display/11.png" title="用户注册"></td>
</tr>
<tr>
  <td><img src="./display/04.png" title="发起问题"></td>
  <td><img src="./display/05.png" title="回复问题"></td>
</tr>
<tr>
  <td><img src="./display/06.png" title="视频专区"></td>
  <td><img src="./display/07.png" title="视频播放"></td>
</tr>
<tr>
  <td><img src="./display/08.png" title="消息通知1"></td>
  <td><img src="./display/12.png" title="消息通知2"></td>
</tr>
<tr>
 <td><img src="./display/09.png" title="图片上传"></td>
  <td><img src="./display/10.png" title="GithubOAuth2"></td>
</tr>
<tr>
 <td><img src="./display/swagger.png" title="swagger"></td>
  <td><img src="./display/swagger2.png" title="swagger2"></td>
</tr>
<tr>
</table>



## 七、 Contributor
[@猕猴桃](https://github.com/XielinX)
