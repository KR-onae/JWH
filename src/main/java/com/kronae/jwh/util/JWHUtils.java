package com.kronae.jwh.util;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public class JWHUtils {
    public static @NotNull String read(File file) throws IOException {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String str = "";
        while(true) {
            String b = br.readLine();
            if (b == null) break;
            str += "\n" + b;
        }
        br.close();
        return str.substring(1);
    }
    public static void write(File file, String str) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(str);
        bw.close();
    }
}
