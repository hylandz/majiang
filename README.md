## Spring-boot学习
+ 使用IDEA自动创建一个springboot项目
+ 在GitHub上创建一个仓库,名称:majiang
+ 使用git命令本地仓库与远程同步

## 使用技术
+ spring-boot
+ mybatis
+ h2,mysql
+ okhttp3
+ lombok
+ flywaydb
+ heroku

## 关于github仓库的注意事项
+ 需要为本地仓库添加一个远程仓库的别名
+ 可以为当前项目设置自己用户名与邮箱,好与其他项目区分

## 为什么md文件写入不到本地文件夹中了?
+ 需要手动刷新IDEA软件与本地磁盘存储的项目

## GitHub oauth2认证的过程
1. 点击第三方登录,认证
  + GET: https://github.com/login/oauth/authorize

需要参数 | 描述
----|:-----
`client_id`|github API给定id(String)
`redirect_uri`|认证成功返回的url(String)
`scope`|获取资源信息范围,user用户,repo仓库(String)
`state`|随机字符串(String)
2. 初次会让你确认是否授权,是,然后会让你登录Github
3. 登录成功后,会返回你指定的redirect_uri,携带参数**code**,**state**
4. 继续向github请求获取token
  + POST:    https://github.com/login/oauth/access_token

需要参数 | 描述
----|:-----
`client_id`|github API给定id(String)
`client_secret`|github API 给定的secret
`redirect_uri`|认证成功返回的url(String)
`scope`|获取资源信息范围,user用户,repo仓库(String)
`state`|随机字符串(String)
`code`|返回的code

5. 获取token后,请求获取github的用户信息
  + GET:    https://api.github.com/user 
需要参数 | 描述
----|:-----
`access_token`|从github那里获取的token



## 脚本
```sql
```
