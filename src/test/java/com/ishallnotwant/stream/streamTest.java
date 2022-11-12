package com.ishallnotwant.stream;

import com.ishallnotwant.dao.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@SpringBootTest
public class streamTest {

    User user1=new User(1,"xx");
    User user2=new User(2,"yy");
    User user3=new User(3,"zz");
    User user4=new User(4,"mm");
    User user5=new User(5,"vv");
    User user6=new User(6,"xx");
    User user7=new User(7,"xx");
    User user8=new User(8,"zz");
    List<User> Users =new ArrayList<>();


    /**
     * 通过流提取对象的某个属性返回集合
     */
    @Test
    public void test001(){

        Users.add(user1);
        Users.add(user2);
        Users.add(user3);
        Users.add(user4);
        Users.add(user5);


        List<Integer> listId= Users.stream().map(User::getId).collect(toList());

        System.out.println(listId);     //[1, 2, 3, 4, 5]

        List<String> listName= Users.stream().map(User::getName).collect(toList());
        String names=listName.toString();
        System.out.println(names.substring(1,names.length()-1));
        System.out.println(listName);   //[xx, yy, zz, mm, vv]

        Map<Integer,String> userMap= Users.stream().collect(Collectors.toMap(User::getId, User::getName));
        System.out.println(userMap.get(3));
        System.out.println(userMap);
    }

    /**
     * 提取相同属性放进map里
     */
    @Test
    public void test002(){
        Users.add(user1);
        Users.add(user2);
        Users.add(user3);
        Users.add(user4);
        Users.add(user5);
        Users.add(user6);
        Users.add(user7);
        Users.add(user8);

        Map<String,List<User>> nameSameMap= Users.stream().collect(Collectors.groupingBy(User::getName));
        System.out.println(nameSameMap);

    }

    @Test
    public void test003(){
        List<String> list = new ArrayList<>();
        list.add("aaa bbb ccc");
        list.add("ddd eee fff");
        list.add("ggg hhh iii");

        //分析 map可以使一个对象转换成另一个对象  这里将string 转换成 string[]对象,在此例中现在得到了3个string[]流
        //flapMap可以将返回的所有流合并
        List<String> list2 = list.stream().map(s -> s.split(" ")).flatMap(Arrays::stream).collect(Collectors.toList());

        System.out.println(list2);
        System.out.println(list);

    }

    @Test
    public void test004(){
        Users.add(user1);
        Users.add(user2);
        Users.add(user7);
        Users.add(user6);
        Users.add(user5);
        Users.add(user4);
        Users.add(user3);

        System.out.println(Users);
        //排序
        Users.sort(Comparator.comparing(User::getId));
        System.out.println(Users);
    }

    @Test
    public void test008(){
        String a1="1";
        String a2="2";
        String a3="3";

        List<String> integers=new ArrayList<>();
        integers.add(a1);
        integers.add(a2);
        integers.add(a3);
        System.out.println(integers);
        String str=integers.toString();
        List<String> a=Arrays.asList(str);
        List<Integer> strings=integers.stream().map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(strings);


    }

    @Test
    public void test009(){
        String a="[[123,54],[4355],[178,123]]";

        String x="[\\]\\[]";

        Pattern p=Pattern.compile(x);

        Matcher m=p.matcher(a);

        String asa=m.replaceAll("").trim();

        List<String> as=Arrays.asList(asa.split(","));

        List<Integer> ids=as.stream().map(Integer::parseInt).collect(Collectors.toList());

        List<String> axx=new ArrayList<>();
        String a11="3270";
        String a12="3267";
        String a13="3272";
        String a14="3266";
        String a15="3270";
        axx.add(a11);
        axx.add(a12);
        axx.add(a13);
        axx.add(a14);
        axx.add(a15);

        List<Integer> idss=axx.stream().map(Integer::parseInt).collect(Collectors.toList());

        System.out.println(as);


    }

}
