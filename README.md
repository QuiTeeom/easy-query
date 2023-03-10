# Easy Query

旨在定义一种查询语法

前后端以及服务之间的查询条件都可以通过统一的语法来实现业务的查询

## 痛点问题
在平时的企业软件开发中,往往会遇到查询的问题

通常情况下,前后端通过接口中,定义个参数在指定查询条件.比如
``` http
name=quitee # 表示查询名字为quitee
fuzzy=true  # 表示是否时模糊查询
```

然而这种方案在遇到复杂查询的时候就很难处理.导致字段越来越多,逻辑越来越变得难以维护,且开始产生歧义.比如
```http
startTime=20220822
startTimeBefore=20220822
startTimeAfter=20220822
```

同时,面对高级查询条件,前端若没有一种合适的方法.很难去设计开发一个支持复杂查询条件的UI组件

## 目标
* 具有较高的可读性
* 对开发人员友好
* 支持复杂 where 查询条件
* 跨语言查询语句构造器
    * java
    * golang
    * js
* 解析并生成对应的实际查询条件

## 阶段
1. 完成where部分的查询条件
    1. 条件语句定义
    2. 条件语句构造器
    3. 条件语句解析器
    4. 生成mysql查询语句
    5. 完善sdk
    6. 集成mybatis,spring等框架
    7. javascript的条件语句构造器

## 特性
### 基本语法
```(<字段> <比较操作> <值>) <组合操作> (<字段> <比较操作> <值>) ... ```

```
name="quitee" AND age = 28
name in("quitee","coco")
(age>=18 AND name contains "q") OR (age between [10,18) AND name match ^quitee)
```

#### 字段

* 无需引号的情况
    * 正常由字母,数字,下划线等无关键字: a1,b_2 ...
* 需要引号的情况
    * 有空格: "a 1"
    * 有关键词: "a=1"

#### 比较操作
* =
* !=
* \>
* <
* \>=
* <=
* in
* not in
* contains
* startWith
* endWith
* between
* match

#### 值类型
* string
* number
* date
* bool

#### 逻辑组合操作
* and
* or


## 快速使用

### 1. 同构ql语句转成sql语句
你直接写表达语句来生成sql
```java
String sql = new MysqlParser().parseQl("name = quitee or name = guhui");
// `name` = 'quitee' OR `name` = 'guhui'
```
你可以自定义表达式关键词
```java
AstBuilder astBuilder = new AstBuilder();
astBuilder.getLexerConfig()
        .withAlias("等于", TokenType.EQ)
        .withAlias("处于", TokenType.IN)
        .withAlias("不等于", TokenType.NOT_EQ);
AstNode astNode = astBuilder.build("(name 等于 quitee and age 不等于 20 ) or ( name 处于 ( 10,30 ))");

MysqlParser parser = new MysqlParser();
String sql = parser.parseAst(astNode);
System.out.println(sql);
// (`name` = 'quitee' AND `age` != '20') OR `name` IN (10,30)
```

 

