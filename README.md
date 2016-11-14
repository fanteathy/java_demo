# Demo开发及发布相关

基于pylon的服务,包括代码结构及开发流程。

## 代码结构

- api: 接口定义相关
	- api: 接口定义
		- OrderApiService: 提供的eleme_order相关的api 
	- dto: 定义请求的数据结构和返回的数据结构(对应javabean)
		- ElemeOrder: `OrderApiService`中接口需要的请求和返回数据结构 
	- exception: 接口exception定义
		- SystemException: 继续自pylon的`SystemException`
		- UserException: 自定义Exception 
- dao: model定义,和DB table crud一一对应，供`service`中接口实现使用
	- ElemeOrderDao: eleme_order表对应的数据模型，和eleme_order表字段对应
	- resources: DB连接，DB事务等
- deploy:
	- 发布相关定义(发布相关变量等)
- docs: 文档
- service: 接口实现,业务逻辑定义。可以加一层BIZ层, service抽象成webapi, biz写详细业务逻辑。依赖`api`和`dao`
	- OrderApiServiceImpl: `OrderApiService`中各种接口的实现    
- pom.xml: 父级pom定义  

## 开发流程:

- 定义接口: api定义, interface定义，写对应的dto
- DAO层: db table crud实现
- service: 业务逻辑
- test: optional
- 发布: 
	- eless配置替换代码中配置
	- ci
	- start.sh
	- accept request

## Pylon相关

### 原理

pylon原理: 提供对应的接口(interface)定义给pylon，服务方实现对应的接口。有对应的请求时，pylon会生成代理对象，调用service对这个接口的具体实现。


### 接口定义
 
 参照`transaction_scoring_system`
 
 由`Configure.json`中的`serverConf`提供对应SOA服务的接口定义
 
 
```
 "serverConf": {
    "name": "pts.score",
    "protocol": "json",
    "group": "local",
    "port": 8088,
    "threadPoolSize": 24,
    "bufferQueueSize": 30,
    "initializer": "me.ele.pts.score.impl.soa.TransactionScoringServiceInitializer",
    "interfaces": [
      "me.ele.pts.score.service.IRestaurantTransactionScore",
      "me.ele.pts.score.service.IUserTransactionScore"
    ]
  },
```
  
其中的`interfaces`定义好了对外提供的接口，同时注册在`huskar`的service中

此处接口定义在`me.ele.pts.api.OrderApiService`中

## 脚本

- crontab sh对应的java程序main函数（不推荐）
- ScheduledThreadPoolExecutor
- jobplus

## 参考

- [Pylon 2.0接入文档](http://wiki.ele.to:8090/pages/viewpage.action?pageId=20333808)或者参考[pdf版本](docs/kuangjiagongju-20333808-141116-1409-4.pdf)

## 其他

- maven-assembly-plugin: 打包，组织代码结构。maven负责打包class文件，resources文件成正确的目录结构
- profile: 环境参数相关（和开发无关，略）
- 多module/多package的结构都可以