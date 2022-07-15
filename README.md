# Tropical Fish

Pragmatic 风格的 Java EE 后端开发脚手架。 基于 SpringBoot，技术选型采用主流的技术框架（Mybatis-Plus，Redisson，Xxl-job，Swagger）。开箱即用，提高研发效能。

多家公司线上产品使用了该脚手架。国内某知名日化企业，已把该脚手架作为基础脚手架，支撑数字化产品的研发。

### 项目特点
1. 自定义 DSL 查询语法。配合 generator 模块，研发人员定义表结构之后，逆向生成代码，单表情况下的CRUD，包括分页查询，可不用写一行代码，就完成开发任务。减轻后端研发人员开发压力，提高研发效率。分页查询语法 在 **自定义 DSL 查询语法** 有做详细的说明。

2. 自研 Excel 报表导入导出工具。 配合自定义查询语法，让导出 Excel 功能的开发和普通条件查询一样简单。高效开发报表导出功能。使用方法参照：BaseUserController.exportByExcel 。

3. 自研 分布式业务编码 生成服务。业务编码可批量生成。生成和使用过程，都是线程安全。 详细参照方法：ActivityOrderBizCodeGenerator.generateNextCode ；generateBizCodeList 。

4. 自定义缓存 @RCacheable 注解，实现分布式缓存。支持 Spel语法，可直接指定 expireTime 。  
   示例：@RCacheable(key = "dmt::miniprogram::token", secKey = "#token", ttl = 1, timeUnit = TimeUnit.DAYS)  

5. 自定义注解 @LoginRequired 注解，可以自动装配当前操作人实体。该注解的意义在于，消除在每个 controller 方法需要手动获取当前操作人的重复性的代码。

6. 自定义 service 方法级别文档生成规则和实现。某种程度上缓解研发人员不爱写文档，又抱怨接手新项目没有文档的尴尬处境。 在**方法调用树示例** 有相应的 json 视图可以看到调用树的数据结构。

   > 参考 <从码农到工匠> 控制代码复杂度的做法。复杂的业务的流程可拆分为多个阶段，每个阶段下有多个子步骤。  自定义注解，过程 @Process， 阶段 @Phase，步骤 @Step。在业务方法的阶段和步骤上加上相应的注解，即可根据请求返回的 TraceId 获取 service 级别的方法调用树。  

    > 研发人员需要按照定义规则流水线化，组件化设计代码，再加上必要的注解，runtime 状态下，就可以得到一颗拥有层次结构的方法调用树，得到复杂业务逻辑的主干架构。每个树结点有 Java 类方法，行数等信息，所见即所得。  

#### 自定义 DSL 查询语法   

> 查询条件语法
> ```json
> {
>    "current":  "页码",
>    "size":  "页数",
>    "modelField.operation":"搜索条件",
>    "orderByDesc":"modelField",
>    "searchKeyword": "关键词"
> }
> ```

> 示例

> ```json
> {
>     "current":1,
>     "size":10,
>     "userName.like":"github",
>     "orderStatus.in":[1,3,4],
>     "createTime.gt":1581392098000,
>     "phone.isNotNull": "",
>     "orderByDesc": "createTime",
>     "searchKeyword": "githu"
> }
> ```

> 查询条件关键字

|    KEYWORD    |                   DESC                    |
| :-----------: | :---------------------------------------: |
|  modelField   |                 模型字段                  |
|       .       |                  分隔符                   |
|  orderByDesc  |                   递减                    |
|  orderByAsc   |                   递增                    |
| searchKeyword | 关键词查询字段，搜索字段需要加上 @Keyword |

>operation 关键字列表

| operation |       DESC        |      语义       |
| :-------: | :---------------: | :-------------: |
|    eq     |       等于        |        =        |
|    ne     |      不等于       |       <>        |
|    gt     |       大于        |        >        |
|    ge     |     大于等于      |       >=        |
|    lt     |       小于        |        <        |
|    le     |     小于等于      |       <=        |
|   like    |     模糊匹配      |    '%value%'    |
| likeLeft  | 以 value 结尾匹配 |    '%value'     |
| likeRight | 以 value 开头匹配 |    'value%'     |
|    in     |        in         |       in        |
|  between  |      闭区间       | between s and e |
|  isNull   |      is null      |      为空       |
| isNotNull |    is not null    |      非空       |



#### 方法调用树示例

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
