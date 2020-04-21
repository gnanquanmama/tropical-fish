# Tropical Fish

Pragmatic 风格的 Java 后端开发脚手架。 基于 SpringBoot，技术选型采用主流的框架（Mybatis-Plus，Redisson，Xxl-job，Dubbo，Swagger）。开箱即用，提高研发效能。

### 项目特点
1. 自定义查询语法，配合 generator 模块, 单表情况下，开发定义好表结构之后，生成代码，可不用写一行代码，就完成开发任务，减轻后端开发任务，提高效率。  

2. 自定义 service 方法级别文档生成规则和实现。某种程度上缓解研发人员不爱写文档，又抱怨接手新项目没有文档的尴尬处境。  

   > 参考<从码农到工匠>控制代码复杂度的做法。复杂的业务的流程可拆分为多个阶段，每个阶段下有多个自步骤。  
   > 自定义注解，过程 @Process， 阶段 @Phase，步骤 @Step。在阶段和步骤方法上加上相应的注解，即可根据请求返回的 TraceId 查询 service 级别的方法调用树。  

   > 研发人员需要按照定义规则流水线化，组件化设计代码，再加上必要的注解，就可以得到一颗拥有层次结构的方法调用树，得到复杂业务逻辑的主干架构。每个树结点有Java类方法，行数等信息，所见即所得。  

3. 自定义缓存 RCacheable 注解，实现分布式缓存。支持 Spel语法，可直接指定 expireTime。  
   示例：@RCacheable(key = "dmt::miniprogram::token", secKey = "#token", ttl = 1, timeUnit = TimeUnit.DAYS)  

#### 自定义查询语法   

> 查询条件语法规则
> ```json
>   {
>       "current":  "页码",
>       "size":  "页数",
>       "modelField_$_operation":"搜索条件",
>       "orderByDesc":"modelField"
>    }
> ```

> 示例

> ```json
>   {
>        "current":1,
>        "size":10,
>        "userName_$_like":"github",
>        "orderStatus_$_in":[1,3,4],
>        "createTime_$_gt":1581392098000,
>        "orderByDesc": "createTime"
>   }
> ```

> 查询条件关键字

|   KEYWORD   |   DESC   |
| :---------: | :------: |
| modelField  | 模型字段 |
|    \_$_     |  分隔符  |
| orderByDesc |   递减   |
| orderByDesc |   递增   |

>Operation取值列表

| Operation |   DESC   |  语义  |
| :-------: | :------: | :----: |
|    eq     |   等于   |   =    |
|    ne     |  不等于  |   <>   |
|    gt     |   大于   |   >    |
|    ge     | 大于等于 |   >=   |
|    lt     |   小于   |   <    |
|    ne     |  不等于  |   <=   |
|   like    | 模糊搜索 | '%值%' |
|    in     |    in    |   in   |

#### 方法调用树

> ```json
> {
>     "id":0,
>     "parentId":-1,
>     "lineNum":80,
>     "method":"UserAuthController.register",
>     "event":"小程序用户注册",
>     "lifeCycle":"process",
>     "sync":true,
>     "childList":[
>         {
>             "id":7,
>             "parentId":0,
>             "lineNum":46,
>             "method":"WechatServiceImpl.getUserInfoByCode",
>             "event":"根据jscode获取用户信息",
>             "lifeCycle":"phase",
>             "sync":true
>         },
>         {
>             "id":8,
>             "parentId":0,
>             "lineNum":25,
>             "method":"BaseUserServiceImpl.getUserByOpenId",
>             "event":"根据openId获取用户信息",
>             "lifeCycle":"phase",
>             "sync":true
>         },
>         {
>             "id":9,
>             "parentId":0,
>             "lineNum":115,
>             "method":"WechatAuthServiceImpl.invalidUserToken",
>             "event":"失效用户token",
>             "lifeCycle":"phase",
>             "sync":true
>         },
>         {
>             "id":10,
>             "parentId":0,
>             "lineNum":43,
>             "method":"WechatAuthServiceImpl.register",
>             "event":"注册用户到DMT系统",
>             "lifeCycle":"phase",
>             "sync":true,
>             "childList":[
>                 {
>                     "id":11,
>                     "parentId":10,
>                     "lineNum":27,
>                     "method":"BaseGenerateCodeServiceImpl.generateNextCode",
>                     "event":"生成用户编码",
>                     "lifeCycle":"step",
>                     "sync":true
>                 }
>             ]
>         },
>         {
>             "id":12,
>             "parentId":0,
>             "lineNum":27,
>             "method":"BaseUserTokenServiceImpl.saveNewToken",
>             "event":"保存新token",
>             "lifeCycle":"phase",
>             "sync":true
>         }
>     ]
> }
> ```
>
> 
>
> 