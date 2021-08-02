# Mapper.xml

- statement标签：select、update、delete、insert分别对应查询、修改、删除、添加操作
  
- parameterType：参数数据类型
    - 基本数据类型，通过id查询Account
    ```xml
    <select id="findById" parameterType="long" resultType="com.zt.entity.Account">
        select * from t_account where id=#{id}
    </select>
    ```
  
    - String类型，通过name查询Account
    ```xml
    <select id="findByName" parameterType="java.lang.String" resultType="com.zt.entity.Account">
        select * from t_account where username=#{username}
    </select>
    ```
  
    - 包装类，通过id查询Account
    ```xml
    <select id="findById" parameterType="java.lang.Long" resultType="com.zt.entity.Account">
        select * from t_account where id=#{id}
    </select>
    ```
    
    - 多个参数，通过username和age查询account，使用arg1、arg1或者param1，param2
    ```xml
    <select id="findByNameAndAge" resultType="com.zt.entity.Account">
        select * from t_account where username=#{arg0} and age=#{arg1}
    </select>
    ```
  
    - java Bean作为参数
    ```xml
    <update id="update" parameterType="com.zt.entity.Account">
        update t_account set username=#{username}, password=#{password}, age=#{age} where id=#{id}
    </update>
    ```
  
- resultType：结果类型
    - 基本数据类型，统计Account总数
    ```xml
    <select id="count" resultType="int">
          select count(id) from t_account
    </select>
    ```
  
    - 包装类，统计Account总数
    ```xml
    <select id="count2" resultType="java.lang.Integer">
        select count(id) from t_account
    </select>
    ```
  
    - String类型，通过id查询Account的name
    ```xml
    <select id="findNameById" parameterType="long" resultType="java.lang.String">
        select username from t_account where id = #{id}
    </select>
    ```
    
    - java bean
    ```xml
    <select id="findById" parameterType="long" resultType="com.zt.entity.Account">
        select * from t_account where id=#{id}
    </select>
    ```
  
