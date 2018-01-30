package com.apabi;

import com.apabi.admin.entity.AuthUser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleTest {
    private Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    @Test
    public void test1() {
        int type = 0;
        double loanAmount = 100000.00;
        int totalDays = 365;
        int day = 10;
        double rate = 0.00045;

        double groupDay = (double) totalDays / (double) day;
        int periods = (int) Math.ceil(groupDay);

        Map<Integer, Object> rateMap = new HashMap<>();
        Map<Integer, Object> accountMap = new HashMap<>();
        Map<Integer, Object> repayMap = new HashMap<>();

        double total = 0.00;
        double amount = loanAmount;
        for (int i = 0; i < periods; i++) {
            if (type == 1) {
                day = getDaysOfMonth(i + 1);
                groupDay = 12;
            }
            accountMap.put(i, amount);
            if (amount <= 0) break;
            double rateVal = (amount * rate) * day;
            total += rateVal;
            double repay = 100000.00 / groupDay;
            amount = amount - repay;
            rateMap.put(i, rateVal);
            repayMap.put(i, rateVal + repay);
        }
        System.out.println(total);
    }

    public static int getDaysOfMonth(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, i - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Test
    public void test2() {
        String filePath = "D:/1111/1.text";
        try {
            File file = new File(filePath);

            boolean b = file.createNewFile();
            System.out.println(b);
//            FileOutputStream out = new FileOutputStream(filePath);
//            String text = "wo you yi zhi xiao mao lv";
//            out.write(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void teset3() {
        AuthUser authUser = new AuthUser();
        authUser.setUsername("aaa");
        AuthUser authUser2 = new AuthUser();
        authUser.setUsername("bbb");
        AuthUser authUser3 = new AuthUser();
        authUser.setUsername("ccc");
        List<AuthUser> list = Arrays.asList(authUser, authUser2, authUser3);
        try {
            System.out.println(list.hashCode());
            List<String> collect = list.stream().map(one -> "00" + one).collect(Collectors.toList());
            System.out.println(collect);
            list.forEach(one -> one.setUsername("000" + one.getUsername()));
            System.out.println(list.hashCode());
        } catch (Exception e) {
            logger.error("Exception:", e);
        }

        List<String> list1 = Arrays.asList("aaa", "bbb", "ccc");
        System.out.println(list1.hashCode());
        System.out.println(list1);
        list1.forEach(one -> one = "000" + one);
        System.out.println(list1);
        System.out.println(list1.hashCode());
    }

    @Test
    public void test34() {
        String a = "aaa_bbb_ccc";
        String b = a.substring(0, a.indexOf("_"));
        System.out.println(b);
        System.out.println(a.indexOf("_"));
        String[] split = a.split("_");
        String collect = Arrays.stream(split).collect(Collectors.joining(","));
        System.out.println(collect);
        String c = "dddd";
        String[] arr = c.split("\\.");
        System.out.println(arr[0]);
    }
}
