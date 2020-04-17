# Tropical Fish   

Pragmatic风格的Java后端开发脚手架。 基于SpringBoot，技术选型采用主流的框架（Mybatis-Plus，Redisson，Xxl-job，Dubbo，Swagger）。开箱即用，提高研发效能。


### 项目特点
1. 自定义查询语法，配合generator模块, 单表情况下，开发定义好表结构之后，生成代码，可不用写一行代码，就完成开发任务，减轻后端开发任务，提高效率。  

2. 自定义service方法级别文档生成规则和实现。某种程度上缓解研发人员不爱写文档，又抱怨接手新项目没有文档的尴尬处境。  

   1. 参考<从码农到工匠>控制代码复杂度的做法。复杂的业务的流程可拆分为多个阶段，每个阶段下有多个自步骤。  
   自定义注解，过程@Process, 阶段@Phase, 步骤@Step。在阶段和步骤方法上加上相应的注解，即可根据请求返回的TraceId查询service级别的方法调用树。  
   
   2. 研发人员需要按照定义规则流水线化，组件化设计代码，再加上必要的注解，就可以得到一颗拥有层次结构的方法调用树，得到复杂业务逻辑的主干架构。每个树结点有Java类方法，行数等信息，所见即所得。  
   
3. 自定义缓存RCacheable注解，实现分布式缓存。支持spel语法，可直接指定expireTime。  
   示例：@RCacheable(key = "dmt::miniprogram::token", secKey = "#token", ttl = 1, timeUnit = TimeUnit.DAYS)  


* #### 自定义查询语法   
  > 查询条件语法规则
    ```json
         {
             "current":  "页码",
             "size":  "页数",
             "modelField_$_operation":"搜索条件",
             "orderByDesc":"modelField"
          }
    ```
  > 示例
    ```json
         {
              "current":1,
              "size":10,
              "userName_$_like":"github",
              "orderStatus_$_in":[1,3,4],
              "createTime_$_gt":1581392098000,
              "orderByDesc": "createTime"
         }
    ```
       
  > 关键字说明       
       
    |KEYWORD| DESC|
    |:----: | :----: |
    | modelField  | 模型字段 |
    | \_$_  | 分隔符 |  
    | orderByDesc  | 递减 | 
    | orderByDesc  | 递增 | 
    
  >Operation取值描述

    |Operation| DESC| 语义| 
    |:----: | :----: | :----:| 
    | eq  | 等于 | = |
    | ne  | 不等于 | <> |
    | gt  | 大于 | > |
    | ge  | 大于等于 | >= |
    | lt  | 小于 | < |
    | ne  | 不等于 | <= |
    | like| 模糊搜索 | '%值%' |
    | in  | in | in |
        
     
---
### 方法调用树示例

    
    process(提交订单）
    │
    └───phase(初始化数据）
    |       └───step
    |           │ 1. 组装上下文
    |           
    └───phase(校验阶段）
    |       └───step
    |           │ 1.库存校验
    |           │ 2.限购校验
    |           │ 3.活动冲突校验
    |           
    |───phase(执行阶段）
    |        └───step
    |             │ 1.预扣库存
    |             │ 2.支付
    |             │ 3.扣减库存
    |             | 4.生成订单
    |────end
