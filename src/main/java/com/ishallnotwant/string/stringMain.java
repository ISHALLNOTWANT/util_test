package com.ishallnotwant.string;

import com.ishallnotwant.dao.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class stringMain {
    public static void main(String[] args) {
        User user1=new User(1,"xx");
        User user2=new User(2,"yy");
        User user3=new User(3,"zz");
        User user4=new User(4,"mm");
        List<User> Users =new ArrayList<>();
        Users.add(user1);
        Users.add(user2);
        Users.add(user3);
        Users.add(user4);
        List<Integer> list= Users.stream().map(User::getId).collect(Collectors.toList());
        List<Integer> list1= Users.stream().map(User::getId).collect(toList());

        list.add(5);

        list1.add(6);

        System.out.println(list);
    }
}
