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
+ `Swagger`

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





## 四、 GitHub oauth2认证的过程
### 第一步: 登录

点击第三方登录,

  > GET:    https://github.com/login/oauth/authorize?

必要参数 | 描述
:----:|:-----:
client_id|`String`github API给定id
redirect_uri|`String`回调url
scop|`String`获取资源信息范围,user用户,repo仓库
state|`String`随机字符串

初次登录会让你确认是否授权,是,然后会让你登录Github,输入密码登录.

授权成功后,会返回到你指定的`redirect_uri`回调地址,并携带参数`code`,`state`

### 第二步: 获取token

通过返回的`code`获取访问的口令 token

  > POST:    https://github.com/login/oauth/access_token

必要参数 | 描述
:----:|:-----:
`client_id`|`String`github API给定id
`client_secret`|`String`github API 给定的secret
`redirect_uri`|`String`回调url
`scope`|`String`获取资源信息范围,user用户,repo仓库
`state`|`String`随机字符串
`code`|`String`返回的code

### 第三步: 获取GitHub的用户信息

获取token后,用于请求获取用户信息,返回JSON 格式的用户信息

  > GET:    https://api.github.com/user?

必要参数 | 描述
----|:-----
`access_token`|从github那里获取的token(String)

## 五、表设计
### 5.1  user表

第三方用户登录

字段|	属性|	描述
:--------:|:----:|:-----
id|	bigint| 主键自增 
acount_id|	varchar100| 第三方账号id 
name|	varchar50| 第三方账号名称 
avatar_url|	varchar100| 第三方账号头像图片ur 
token|	varchar36| 密钥,cookie访问 
gmt_create|	bigint| 创建时间 
gmt_modified|	bigint| 修改时间 

### 5.2 question表

问题表,发布问题

字段|	属性|	描述
:--------:|:----:|:-----
id|	bigint| 主键自增 
title|varchar50|问题标题
description|text|问题内容
comment_count|int10|评论数量
view_count|int10|浏览数量
like_count|int10|点赞数量
tag|varchar256|标签,(标签样式)
creator|bigint|创建/发出问题的人
gmt_create|	bigint| 创建时间 
gmt_modified|	bigint| 修改时间 

### 5.3 notification表

消息通知,

字段|	属性|	描述
:--------:|:----:|:-----
id|	bigint| 主键自增 
notifier|bigint|发送通知人id
notifier_name|varchar50|发送通知人的姓名,`XXX某某某`
receiver|bigint|接收通知人,回复了谁发的问题,谁发的评论id`
outer_id|	bigint|问题id`问题id,得到title`
type|int10|通知类型,1:回答了问题,2:回复了评论,②回答了问题/回复了评论`
status|int10|通知状态,默认0:未读,1:已读,标记问题状态,如未读红色标记`
outer_title|varchar256|问题的标题,③问题[为什么离职]`
gmt_create|	bigint| 创建时间, 
gmt_modified|	bigint| 修改时间, 

### 5.4 comment表

回答问题,评论

字段|	属性|	描述
:--------:|:----:|:-----
id|	bigint| 主键自增 
parent_id|bigint|答问题,parent_id=该问题id,评论答的问题,parent_id=回答的问题的主键id,`只能对某个问题的回答做出一级评论,不能针对个人评论层层递进`
type|int10|类型,回答该问题1或评论该问题的回答2`
commentator|bigint|用户id,谁回答?,谁评论的?`
comment|varchar1024|回答内容,评论内容`
comment_count|int|回复统计,默认0`你发表的多少人回复你`
like_count|int|点赞统计
gmt_create|	bigint| 创建时间 
gmt_modified|	bigint| 修改时间 

### 5.5 Account 表

用户注册

| 字段         | 属性    | 描述          |
| ------------ | ------- | ------------- |
| id           | bigint  | 主键自增,Long |
| user_name    | varchar | 用户名        |
| password     | varchar | 密码          |
| email        | varchar | 邮箱          |
| phone        | varchar | 手机号码      |
| gmt_create   | bigint  | 创建时间      |
| gmt_modified | bigint  | 修改时间      |
|              |         |               |



## 六、 项目演示

访问URL: http://localhost:8887/ 

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
