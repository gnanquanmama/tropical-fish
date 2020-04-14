#Tropical Fish
基于springboot的后端开发脚手架, ORM采用mybatis-plus，缓存框架使用Redisson，接口文档使用swagger。


### 项目特点
1. 自定义查询语法，配合 mybatis-plus-generate-code项目, 单表情况下，开发定义好表结构之后，生成代码，可不用写一行代码，就完成开发任务，减轻后端开发任务，提高效率。
2. 自定义缓存RCacheable注解，实现分布式缓存。支持spel语法，可直接指定expireTime。  
示例：@RCacheable(key = "dmt::miniprogram::token", secKey = "#token", ttl = 1, timeUnit = TimeUnit.DAYS)
3. 参考<从码农到工匠>控制代码复杂度的做法。复杂的业务的流程可拆分为多个阶段，每个阶段下有多个自步骤。   
自定义注解，过程@Process, 阶段@Phase, 步骤@Step。在阶段和步骤方法上加上相应的注解，即可根据请求返回的TraceId查询service级别的方法调用树。   
开发可以不写文档，就可以得到一颗调用树，得到复杂业务逻辑的主干架构，所讲即所得。某种程度上解决了开发不爱写文档，又抱怨新接手项目没有文档的尴尬处境。 


#### 自定义查询语法   
    1.查询条件语法规则：
      {
         "current":  页码,
         "size":  页数,
         "modelField_$_operation":"搜索条件",
         "orderByDesc":"指定字段"
      }
      
      说明：modelField 对应的是接口返回的字段名称，例如userName。
      排序使用orderByDesc，orderByAsc
      
    2.分隔符使用：_$_
    3.operation  取值如下：
               eq  等于  =
               ne  不等于  <>
               gt  大于  >
               ge  大于等于  >=
               lt  小于  <
               le  小于等于  <=
               like  LIKE  '%值%'
               in  IN  []
    4.DEMO：
      {
              "current":1,
              "size":10,
              "userName_$_like":"github",
              "orderStatus_$_in":[1,3,4],
              "startTime_$_gt":1581392098000,
              "orderByDesc": "createTime"
      }
     
#### 方法调用树
    
    process(提交订单）
    │
    └───phase(初始化数据）
    |       │   
    |       └───step
    |           │ 1. 组装上下文
    |           
    └───phase(校验阶段）
    |       |   
    |       └───step
    |           │ 1.库存校验
    |           │ 2.限购校验
    |           │ 3.活动冲突校验
    |           
    |───phase(执行阶段）
    |        │   
    |        └───step
    |             │ 1.预扣库存
    |             │ 2.支付
    |             │ 3.扣减库存
    |             | 4.生成订单
    |────end