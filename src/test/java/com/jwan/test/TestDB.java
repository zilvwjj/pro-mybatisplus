package com.jwan.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwan.dao.UserDao;
import com.jwan.domain.User;
import com.jwan.domain.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class TestDB {
    @Autowired
    private UserDao userDao;
    @Test
    public void testDao(){
        List<User> users = userDao.selectList(null);
        System.out.println(users);
    }
    @Test
    public void testDaoInsert(){
        User user = new User(null,"测试insert","1234",23,"12121212");
        int returnVal = userDao.insert(user);
        System.out.println(returnVal);
    }
    @Test
    public void testDaoDeleteById(){
        int returnVal = userDao.deleteById(9L);
        System.out.println(returnVal);
    }
    @Test
    public void testDaoUpdateById(){
        User user = new User(23L,"测试UpdateById","1234fff",23,"12121212");
        int returnVal = userDao.updateById(user);
        System.out.println(returnVal);
    }
    @Test
    public void testSelectById(){
        User user = userDao.selectById(9L);
        System.out.println(user);
    }
    @Test
    public void testSelectList(){
        List<User> users = userDao.selectList(null);
        System.out.println(users);
    }
    @Test
    public void testSelectPage(){
        IPage<User> userPage = new Page<>(1, 4);
        userDao.selectPage(userPage,null);
        log.info("当前页码值:{},每页显示数:{},一共多少页:{},一共多少条数据:{},数据:{}"
        ,userPage.getCurrent(),userPage.getSize(),userPage.getPages()
                ,userPage.getTotal(),userPage.getRecords());

    }
    @Test
    public void testSelectWrapper(){
        QueryWrapper<User> qw1 = new QueryWrapper<>();
        qw1.lt("age",18);//age < 18
        List<User> users = userDao.selectList(qw1);
        System.out.println(users);

        System.out.println("接着来看第二种:==QueryWrapper的基础上使用lambda");
        QueryWrapper<User> qw2 = new QueryWrapper<>();
        qw2.lambda().lt(User::getAge,10);
        List<User> users2 = userDao.selectList(qw2);
        System.out.println(users2);

        System.out.println("接着来看第三种:==LambdaQueryWrapper");
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.lt(User::getAge,10);
        List<User> users3 = userDao.selectList(lqw);
        System.out.println(users3);

    }
    @Test
    public void testMultipleWrapper(){
        System.out.println();
        System.out.println("type one");
        LambdaQueryWrapper<User> qw1 = new LambdaQueryWrapper<>();
        qw1.lt(User::getAge,18);
        qw1.gt(User::getAge,10);
        System.out.println(userDao.selectList(qw1));

        System.out.println();
        System.out.println("链式编程");
        LambdaQueryWrapper<User> qw2 = new LambdaQueryWrapper<>();
        qw2.lt(User::getAge,18).gt(User::getAge,10);
        System.out.println(userDao.selectList(qw2));

        System.out.println();
        System.out.println("or()");
        LambdaQueryWrapper<User> qw3 = new LambdaQueryWrapper<>();
        qw3.gt(User::getAge,10).or().lt(User::getAge,18);
        System.out.println(userDao.selectList(qw3));
    }

    /**
     * 需求:查询数据库表中，根据输入年龄范围来查询符合条件的记录
     *
     * 用户在输入值的时候，
     *
     * 	如果只输入第一个框，说明要查询大于该年龄的用户
     *
     * 	如果只输入第二个框，说明要查询小于该年龄的用户
     *
     *  如果两个框都输入了，说明要查询年龄在两个范围之间的用户
     */
    @Test
    public void testMultipleWrapperNull(){
        UserQuery userQuery = new UserQuery();
        userQuery.setAge(10);
        userQuery.setAge2(30);
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.lt(null!= userQuery.getAge2(),User::getAge,userQuery.getAge2());
        lqw.gt(null!= userQuery.getAge(),User::getAge,userQuery.getAge());
        System.out.println(userDao.selectList(lqw));
    }

    @Test
    public void testSelectColumn(){
        System.out.println("查询指定字段");
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.select(User::getId,User::getName,User::getTel);
        List<User> users = userDao.selectList(lqw);
        System.out.println(users);
    }

    @Test
    public void testSelectCountMaxMinAvgSum(){
        System.out.println("聚合查询");
        System.out.println("Count");
        QueryWrapper<User> qwCount = new QueryWrapper<>();
        qwCount.select("count(*) as count");
        List<Map<String, Object>> maps = userDao.selectMaps(qwCount);
        System.out.println(maps);
        System.out.println("Max");
        QueryWrapper<User> qwMax = new QueryWrapper<>();
        qwMax.select("Max(age) as maxAge");
        List<Map<String, Object>> maps1 = userDao.selectMaps(qwMax);
        System.out.println(maps1);
        System.out.println("min");
        QueryWrapper<User> qwMin = new QueryWrapper<>();
        qwMin.select("Min(age) as minAge");
        List<Map<String, Object>> maps2 = userDao.selectMaps(qwMin);
        System.out.println(maps2);
        System.out.println("avg");
        QueryWrapper<User> qwAvg = new QueryWrapper<>();
        qwAvg.select("Avg(age) as avgAge");
        List<Map<String, Object>> maps3 = userDao.selectMaps(qwAvg);
        System.out.println(maps3);
        System.out.println("Sum");
        QueryWrapper<User> qwSum = new QueryWrapper<>();
        qwSum.select("Sum(age) as sumAge");
        List<Map<String, Object>> maps4 = userDao.selectMaps(qwSum);
        System.out.println(maps4);

    }

    @Test
    public void testSelectGroupBy(){
        System.out.println("分组查询");
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.select("count(*) as count, tel").groupBy("tel");
        List<Map<String, Object>> maps = userDao.selectMaps(qw);
        System.out.println(maps);
    }

    @Test
    public void testSelectEq(){
        System.out.println("等值查询");
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getName,"Tom").eq(User::getPassword,"tom");
        User user = userDao.selectOne(lqw);
        System.out.println(user);
    }

    @Test
    public void testSelectRange(){
        System.out.println("lt,gt,ge,gt,between");
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.between(User::getAge,10,30);
        List<User> users = userDao.selectList(lqw);
        System.out.println(users);
    }

    @Test
    public void testSelectLike(){
        System.out.println("模糊查询");
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.likeRight(User::getTel,"1");
        List<User> users = userDao.selectList(lqw);
        System.out.println(users);
    }

    @Test
    public void testSelectOrderBy(){
        System.out.println("排序查询");
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        /**
         * condition ：条件，返回boolean，
         当condition为true，进行排序，如果为false，则不排序
         * isAsc:是否为升序，true为升序，false为降序
         * columns：需要操作的列
         */
        lqw.orderBy(true,false,User::getAge);
        List<User> users = userDao.selectList(lqw);
        System.out.println(users);
    }

    @Test
    public void testSelectBatchIds(){
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        List<User> users = userDao.selectBatchIds(ids);
        System.out.println(users);
    }

    @Test
    public void testDeleteBatchIds(){
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(5L);
        ids.add(6L);
        ids.add(7L);
        int i = userDao.deleteBatchIds(ids);
        System.out.println(i);
    }

    @Test
    public void testSelectAll(){
        System.out.println(userDao.selectAll());
    }

    @Test
    public void testVersion(){
        System.out.println("测试乐观锁");
        User user = userDao.selectById(9L);
        user.setName("测试version");
        userDao.updateById(user);
    }

    @Test
    public void testVersionLock(){
        System.out.println("模拟加锁情况");
        User user1 = userDao.selectById(9L);
        User user2 = userDao.selectById(9L);
        user1.setName("测试version1");
        userDao.updateById(user1);
        user2.setName("测试version2");
        userDao.updateById(user2);
    }
}
