package com.forDece;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Arrays.asList(1, 2, 3, 4, 5, 6, 7)
                .parallelStream().map(num -> num * 2)
                .filter(num -> num > 3)
                .limit(6)
                .forEach(System.out::println);


        try (FileReader fr = new FileReader(new File(""));
             BufferedReader br = new BufferedReader(fr)) {
            // code ...

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // try-with-resource 语法，网络、文件、数据库等资源不由jvm管理，
        // 需要我们手动释放资源，br.close();
        // 连接资源用完即释放


        try (OutputStream os = new FileOutputStream("test.txt")) {
            // 自动释放资源
            os.flush();
            // flush 将缓冲去写入到文件中
            // 默认缓冲区满了才写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static <T extends Comparable<T>> T maximum(T x, T y, T z) {
        T max = x;
        if (y.compareTo(max) > 0) {
            max = y;
        }
        if (z.compareTo(max) > 0) {
            max = z;
        }
        return max;
    }

}


class Box<T> {

}