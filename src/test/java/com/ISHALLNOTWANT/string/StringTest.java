package com.ISHALLNOTWANT.string;

import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: Wang Zhen
 * @CreateDate: 2022/10/14
 **/
@SpringBootTest
public class StringTest {

    @Test
    public void test001(){
        String a="[[123,54],[4355],[178,123]]";

    }

    @Test
    public void test002() {
        List<Integer> test = new ArrayList<>();
        Integer a11 = 1;
        Integer a12 = 2;
        Integer a13 = 2;
        Integer a14 = 2;
        Integer a15 = 4;
        test.add(a11);
        test.add(a12);
        test.add(a13);
        test.add(a14);
        test.add(a15);
        int rank = 1;
        for (int i = 0; i < test.size(); i++) {
            //处理第一条数据
            if (i > 0) {
                //判断和前一个是否相等
                if (!Objects.equals(test.get(i), test.get(i - 1))) {
                    //不和前一个相等,rank更新为最先和前面不等的排名
                    rank = i + 1;
                }

            }
            System.out.println(rank);
        }
    }
}
