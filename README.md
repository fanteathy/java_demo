# Demo开发及发布相关

基于pylon的服务,包括代码结构及开发流程。

## 代码结构

- api: 接口定义相关
	- api: 接口定义
		- OrderApiService: 提供的eleme_order相关的api 
	- dto: 定义请求的数据结构和返回的数据结构(对应javabean)
		- ElemeOrder: `OrderApiService`中接口需要的请求和返回数据结构
	- form: 也可以使用dto定义返回的数据结构，form定义请求的数据结构
	- exception: 接口exception定义
		- ServiceException: 表示业务相关的异常(如用户不存在，红包已过期等)
		- ServerException: 表示服务内部的异常(如数据库连接超时，redis服务不可用等)
		- java.lang.RuntimeException: 非受检异常, 计入熔断统计
		- 其余异常参考: [Pylon内置Rpc异常](http://wiki.ele.to:8090/pages/viewpage.action?pageId=20328819)
- dao: model定义,和DB table crud一一对应，供`service`中接口实现使用
	- ElemeOrderDao: eleme_order表对应的数据模型，和eleme_order表字段对应
	- resources: DB连接，DB事务等
- deploy:
	- 发布相关定义(发布相关变量等)
- docs: 文档
- service: 接口实现,业务逻辑定义。可以加一层BIZ层, service抽象成webapi, biz写详细业务逻辑。依赖`api`和`dao`
	- conf: 主要的配置文件
		- Configure.json: 最重要的配置文件。
			- serverConf: 服务端配置
				- initializer: IServiceInitializer实现类类名
				- interfaces: 提供服务的接口名列表
			- clientConfs: 客户端配置, 有依赖服务则需要声明
				- interfaces: 调用的接口名列表
	- OrderApiServiceImpl: `OrderApiService`中各种接口的实现
	- soa: 实现Pylon接口
		- ServiceInitializer: 服务初始化接口, 服务启动的时候调用对应init方法
			- getImpl: 返回指定接口对应的实现实例(所以能够通过提供的interface名找到对应的方法实现类)
	- MainApplication: `程序启动入口`, 启动基于Pylon的服务(其实是调用`ServiceInitializer.init()`) 。也可以通过me.ele.core.container.Container作为MainClass启动。(start.sh中配置启动入口)
- common: 定义公共组件
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

- 打包: 使用maven-assembly-plugin插件，配合dist.xml，conf目录存放配置文件，bin目录存放可执行脚本，lib目录存放所有依赖jar包。
- profile: 环境参数相关（和开发无关，略）
- 多module/多package的结构都可以