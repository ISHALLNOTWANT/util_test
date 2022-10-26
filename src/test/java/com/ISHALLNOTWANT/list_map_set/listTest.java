package com.ISHALLNOTWANT.list_map_set;

import com.ISHALLNOTWANT.dao.user;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class listTest {
    user user1=new user(1,"xx");
    user user2=new user(2,"yy");
    user user3=new user(3,"zz");
    user user4=new user(4,"mm");

    user user4_2=new user(4,"aa");

    user user5=new user(5,"vv");
    List<user> users=new ArrayList<>();

    @Test
    public void test001(){
        users.add(user4_2);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        //sorted是流的方法，不会改变原来的数据，排序完会返回一个新的
        List<user> users1=users.stream().sorted(Comparator.comparing(user::getId).reversed()).collect(Collectors.toList());
        System.out.println(users1);
        System.out.println(users);

        //sort会直接在原集合上直接进行排序
        users.sort(Comparator.comparing(user::getId).reversed());
        System.out.println(users);

        Collections.reverse(users1);
        //先按照id排序再按照名字排序
        users1.sort(Comparator.comparing(user::getId).thenComparing(user::getName));
        System.out.println(users1);

    }

}
