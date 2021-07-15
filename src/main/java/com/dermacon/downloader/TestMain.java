package com.dermacon.downloader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {

    public static void main(String[] args) {
        String test = "https://www.youtube.com/watch?v=9ju-Qj8xFQk";
//        String test = "https://www.youtube.com/watch?v=9ju-Qj8xFQk&list=RDMM&start_radio=1&rv=yA2U7fl0xb8";
//        String test = "https://www.youtube.com/watch?v=9ju-Qj8xFQk&list=RDMM&start_radio=1&rv" +
//                "=yA2U7fl0xb8"    ;

//        String regex = ".*youtube.*?v=(.*)|.*youtube.*?v=(.*)&list.*";
        String regex = ".*youtube.*?v=(.*)&list.*|.*youtube.*?v=(.*)";
//        String regex = ".*youtube.*?v=(.*)";
        System.out.println(test.matches(regex));
        // Create a Pattern object
        Pattern r = Pattern.compile(regex);

        // Now create matcher object.
        Matcher m = r.matcher(test);

        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
        } else {
            System.out.println("NO MATCH");
        }
    }

}
