package com.zt.test;

import com.zt.entity.*;
import com.zt.repository.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class Test1 {

    @Test
    public void testNativeInterface() {
        // 加载mybatis配置文件
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        String statement = "com.zt.mapper.AccountMapper.save";

        Account account = new Account(1L, "张三", "123123", 22);

        sqlSession.insert(statement, account);

        sqlSession.commit();

        sqlSession.close();
    }

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

    @Test
    public void testUserDefineInterfaceSelectById() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 获取实现接口的代理对象
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        Account account = accountRepository.findById(1L);
        // 不因为没有改变数据库的内容，所以不需要commit
        System.out.println(account);
        sqlSession.close();
    }

    @Test
    public void testUserDefineInterfaceInsert() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        // 添加对象
        Account account = new Account(3L, "王五", "985723", 23);
        accountRepository.save(account);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testUserDefineInterfaceUpdate() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        // 查询对象
        Account account = accountRepository.findById(3L);
        // 修改对象
        account.setUsername("小明");
        // 更新对象
        accountRepository.update(account);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testUserDefineInterfaceDeleteById() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        // 删除对象
        accountRepository.deleteById(3L);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testUserDefineInterfaceFindByName() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        // 查询对象
        Account account = accountRepository.findByName("王五");
        System.out.println(account);
        sqlSession.close();
    }

    @Test
    public void testUserDefineInterfaceFindByNameAndAge() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        // 查询对象
        Account account = accountRepository.findByNameAndAge("王五", 23);
        System.out.println(account);
        sqlSession.close();
    }

    @Test
    public void testUserDefineInterfaceCount() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        // 查询对象
        int count = accountRepository.count();
        System.out.println(count);
        sqlSession.close();
    }

    @Test
    public void testUserDefineInterfaceCount2() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        // 查询对象
        int count = accountRepository.count2();
        System.out.println(count);
        sqlSession.close();
    }

    @Test
    public void testUserDefineInterfaceFindNameById() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        // 查询对象
        String name = accountRepository.findNameById(1L);
        System.out.println(name);
        sqlSession.close();
    }

    @Test
    public void testOneToMultiFindById() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        StudentRepository studentRepository = sqlSession.getMapper(StudentRepository.class);
        // 查询对象
        Student student = studentRepository.findById(1L);
        System.out.println(student);
        sqlSession.close();
    }

    @Test
    public void testOneToMultiFindClassesById() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        ClassesRepository classesRepository = sqlSession.getMapper(ClassesRepository.class);
        // 查询对象
        Classes classes = classesRepository.findById(2L);
        System.out.println(classes);
        sqlSession.close();
    }

    @Test
    public void testCustomerMultiToMultiFindById() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        CustomerRepository customerRepository = sqlSession.getMapper(CustomerRepository.class);
        // 查询对象
        Customer customer = customerRepository.findById(1L);
        System.out.println(customer);
        sqlSession.close();
    }

    @Test
    public void testGoodsMultiToMultiFindById() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        GoodsRepository goodsRepository = sqlSession.getMapper(GoodsRepository.class);
        Goods goods = goodsRepository.findById(1L);
        System.out.println(goods);
        sqlSession.close();
    }

    @Test
    public void testFindByAccount() {
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取实现接口的代理对象
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        Account account = new Account(4L, null, "985723", 23);
        Account account1 = accountRepository.findByAccount(account);
        System.out.println(account1);
        sqlSession.close();
    }
}
