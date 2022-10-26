package com.ISHALLNOTWANT.string;

import com.ISHALLNOTWANT.dao.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class stringMain {
    public static void main(String[] args) {
        user user1=new user(1,"xx");
        user user2=new user(2,"yy");
        user user3=new user(3,"zz");
        user user4=new user(4,"mm");
        List<user> users=new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        List<Integer> list=users.stream().map(user ::getId).collect(Collectors.toList());
        List<Integer> list1=users.stream().map(user ::getId).collect(toList());

        list.add(5);

        list1.add(6);

        System.out.println(list);
    }
}
