package com.ishallnotwant.list_map_set;

import com.ishallnotwant.dao.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class listTest {
    User user1=new User(1,"xx");
    User user2=new User(2,"yy");
    User user3=new User(3,"zz");
    User user4=new User(4,"mm");

    User user4_2=new User(4,"aa");

    User user5=new User(5,"vv");
    List<User> Users =new ArrayList<>();

    @Test
    public void test001(){
        Users.add(user4_2);
        Users.add(user1);
        Users.add(user2);
        Users.add(user3);
        Users.add(user4);
        Users.add(user5);

        //sorted是流的方法，不会改变原来的数据，排序完会返回一个新的
        List<User> users1= Users.stream().sorted(Comparator.comparing(User::getId).reversed()).collect(Collectors.toList());
        System.out.println(users1);
        System.out.println(Users);

        //sort会直接在原集合上直接进行排序
        Users.sort(Comparator.comparing(User::getId).reversed());
        System.out.println(Users);

        Collections.reverse(users1);
        //先按照id排序再按照名字排序
        users1.sort(Comparator.comparing(User::getId).thenComparing(User::getName));
        System.out.println(users1);

    }

}
