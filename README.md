# MyBatis

MyBatis实现ORMapping（Object Relationship Mapping，对象关系映射），开发者可以使用面向对象的思想管理
数据库

SqlSessionFactoryBuilder-->build()-->SqlSessionFactory-->openSession()-->SqlSession

-  使用原生接口
- Mapper代理实现自定义接口

# 如何使用

- 新建maven工程，pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>learnMyBatis</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.5</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.11</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.20</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/junit/junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>

    <!-- 配置xml文件读取路径 -->
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>*.xml</include>
                    <include>*.properties</include>
                </includes>
            </resource>
        </resources>
    </build>

</project>
```

- 新建数据表

```sql
CREATE TABLE t_account(
    id int primary key auto_increment,
    username varchar(11),
    password varchar(11),
    age int
);
```

- 新建数据表对应的实体类acount

```java
package com.zt.entity;

import lombok.Data;

@Data
public class Account {
    private long id;
    private String username;
    private String password;
    private int age;
}
```

- 创建mybatis配置文件config.xml，文件名可自定义

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!-- 配置mybatis运行环境 -->
    <environments default="development">
        <environment id="development">
            <!-- 配置jdbc事务管理 -->
            <transactionManager type="JDBC"></transactionManager>
            <!-- POOLED配置JDBC数据源连接池 -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useUnicode=true&amp;characterEncoding=UTF-8"/>
                <property name="username" value="root"/>
                <property name="password" value="123456789"/>
            </dataSource>
        </environment>
    </environments>
</configuration>
```

## 使用原生接口

### 第一步

MyBatis框架需要开发者自定义SQL语句，写在Mapper.xml文件中，实际开发中会为每一个实体类创建对应的Mapper.xml，
定义管理该对象数据的SQL。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zt.mapper.AccountMapper">
    <insert id="save" parameterType="com.zt.entity.Account">
        insert into t_account(username, password, age) values(#{username}, #{password}, #{age})
    </insert>
</mapper>
```

- namespace通常设置为文件所在包+文件名的形式，不需要文件扩展名
- insert标签表示执行添加操作
- select标签表示执行查询操作
- update标签表示执行添更新操作
- delete标签表示执行删除操作
- id是实际调用mybatis方法时需要用到的参数
- parameterType是调用对应方法时参数的数据类型

### 第二步

在全局配置文件config.xml中注册AccountMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!-- 配置mybatis运行环境 -->
    <environments default="development">
        <environment id="development">
            <!-- 配置jdbc事务管理 -->
            <transactionManager type="JDBC"></transactionManager>
            <!-- POOLED配置JDBC数据源连接池 -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useUnicode=true&amp;characterEncoding=UTF-8"/>
                <property name="username" value="root"/>
                <property name="password" value="123456789"/>
            </dataSource>
        </environment>
    </environments>
    
    <!-- 注册AccountMapper.xml -->
    <mappers>
        <mapper resource="com/zt/mapper/AccountMapper.xml"></mapper>
    </mappers>
</configuration>
```

### 第三步

- 调用mybatis的原生接口，执行添加操作。

```java
public class Test {
    public static void main(String[] args) {
        // 加载mybatis配置文件
        InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        String statement = "com.zt.mapper.AccountMapper.save";

        Account account = new Account(1L, "张三", "123123", 22);

        sqlSession.insert(statement, account);

        sqlSession.commit();

        sqlSession.close();
    }
}
```

- 通过Mapper代理实现自定义接口

1、自定义接口和相关业务方法

```java
package com.zt.repository;

import com.zt.entity.Account;

import java.util.List;

public interface AccountRepository {
    public int save(Account account);
    public int update(Account account);
    public int deleteById(long id);
    public List<Account> findAll();
    public Account findById(long id);
}
```

2、创建接口对应的Mapper.xml，定义接口方法对应的SQL语句

statement标签可根据SQL执行的业务选择insert、delete、update和select。

mybatis框架会根据规则自动创建接口实现类的代理对象

规则：

- Mapper.xml中namespace为接口的全类名
- Mapper.xml中statement的id为接口中对应的方法名
- Mapper.xml中statement的parameterType和接口中对应方法的参数类型一致
- Mapper.xml中statement的resultType和接口中对应方法的返回值类型一致

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zt.repository.AccountRepository">
    <insert id="save" parameterType="com.zt.entity.Account">
        insert into t_account(username, password, age) values(#{username}, #{password}, #{age})
    </insert>

    <update id="update" parameterType="com.zt.entity.Account">
        update t_account set username=#{username}, password=#{password}, age=#{age} where id=#{id}
    </update>

    <delete id="deleteById" parameterType="long">
        delete from t_account where id=#{id}
    </delete>

    <select id="findAll" resultType="com.zt.entity.Account">
        select * from t_account
    </select>

    <select id="findById" parameterType="long" resultType="com.zt.entity.Account">
        select * from t_account where id=#{id}
    </select>
</mapper>
```

3、在config.xml中注册AccountRepository.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!-- 配置mybatis运行环境 -->
    <environments default="development">
        <environment id="development">
            <!-- 配置jdbc事务管理 -->
            <transactionManager type="JDBC"></transactionManager>
            <!-- POOLED配置JDBC数据源连接池 -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useUnicode=true&amp;characterEncoding=UTF-8"/>
                <property name="username" value="root"/>
                <property name="password" value="123456789"/>
            </dataSource>
        </environment>
    </environments>

    <!-- 注册AccountMapper.xml -->
    <mappers>
        <mapper resource="com/zt/mapper/AccountMapper.xml"></mapper>
        <mapper resource="com/zt/repository/AccountRepository.xml"></mapper>
    </mappers>
</configuration>
```

4、调用接口的代理对象完成相关的业务操作

```java
@Test
public void testUserDefineInterfaceSelectAll() {
    InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    // 获取实现接口的代理对象
    AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
    List<Account> list = accountRepository.findAll();
    // 不因为没有改变数据库的内容，所以不需要commit
    for (Account account : list) {
        System.out.println(account);
    }
    sqlSession.close();
}
```

# Ref

1、mybatis视频教程：https://www.bilibili.com/video/BV1V7411w7VW?from=search&seid=13599583476053237383

2、mysql索引：https://www.bilibili.com/video/BV19y4y127h4?p=3&spm_id_from=pageDriver

3、mysql全讲解：https://time.geekbang.org/column/intro/139
