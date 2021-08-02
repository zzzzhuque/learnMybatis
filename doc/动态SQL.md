# 动态SQL

使用动态SQL可简化代码开发，减少工作量，程序可以自动根据也无参数来决定SQL的组成

- if标签

```java
<select id="findByAccount" parameterType="com.zt.entity.Account" resultType="com.zt.entity.Account">
        select * from t_account
    <where>
        <if test="id!=0">
            id=#{id}
        </if>
        <if test="username!=null">
            and username=#{username}
        </if>
        <if test="password!=null">
            and password=#{password}
        </if>
        <if test="age!=0">
            and age=#{age}
        </if>
    </where>
</select>
```

if标签可以自动根据表达式的结果来决定是否将对应的语句添加到SQL中，如果条件不成立
则不添加，如果条件成立则添加

- where标签

可以自动判断是否要删除语句块中的and关键字，如果检测到where直接跟and拼接，则自动
删除where，通常情况下if和where结合起来使用

- choose、when标签

```xml
<select id="findByAccount" parameterType="com.zt.entity.Account" resultType="com.zt.entity.Account">
    select * from t_account
    <where>
        <choose>
            <when test="id!=0">
                id = #{id}
            </when>
            <when test="username!=null">
                username = #{username}
            </when>
            <when test="password!=null">
                password = #{password}
            </when>
            <when test="age!=0">
                age=#{age}
            </when>
        </choose>
    </where>
</select>
```

- trim标签

trim标签中的prefid和suffix属性会被用于生成实际的SQL语句，会和标签内部的语句进行拼接，
如果语句前后出现了prefixOverrides或者suffixOverrides属性中指定的值，MyBatis会自动
将其删除

```xml
<select id="findByAccount" parameterType="com.zt.entity.Account" resultType="com.zt.entity.Account">
    select * from t_account
    <trim prefix="where" prefixOverrides="and">
        <if test="id!=0">
            id=#{id}
        </if>
        <if test="username!=null">
            and username=#{username}
        </if>
        <if test="password!=null">
            and password=#{password}
        </if>
        <if test="age!=0">
            and age=#{age}
        </if>
    </trim>
</select>
```

- set标签

set标签用于update操作，会自动根据参数选择生成SQL语句