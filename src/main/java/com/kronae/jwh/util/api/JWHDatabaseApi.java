package com.kronae.jwh.util.api;

import com.kronae.jwh.JWH;
import com.kronae.jwh.exception.ParseException;
import com.kronae.jwh.util.JWHUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.NotDirectoryException;
import java.util.Arrays;

public class JWHDatabaseApi implements JWHApi {
    private File dbDir;

    public JWHDatabaseApi(@NotNull File databaseDirectory) throws NotDirectoryException {
        if(databaseDirectory.isDirectory()) {
            this.dbDir = databaseDirectory;
        } else {
            throw new NotDirectoryException("This is not a directory!");
        }
    }
    @Override
    public void setup(JWH web) {
    }
    @Nullable
    public String getDatabaseValueCustom(String @NotNull [] path) throws IOException {
        File dir = dbDir;
        for (int i = 0; i < path.length; i++) {
            String p = path[i];
            dir = new File(dir.getAbsolutePath().replace("/", "\\") + "\\" + p);
            if(!dir.exists())
                dir.mkdir();
            if(!dir.isDirectory())
                throw new NotDirectoryException("This is not a directory: " + dir.getAbsolutePath());

            if(!new File(dir.getAbsolutePath() + "\\" + "directory").exists()) {
                if(i == path.length - 1) {
                    return JWHUtils.read(new File(dir.getAbsolutePath() + "\\" + "content"));
                } else {
                    throw new RuntimeException("This is a directory but this directory is not a directory.");
                }
            }
        }
        return null;
    }
    @Nullable
    public String getDatabaseValueCustom(@NotNull String path) throws IOException {
        return getDatabaseValueCustom(path.split("\\."));
    }
    public void setDatabaseValueCustom(String @NotNull [] path, String value) throws IOException {
        File dir = dbDir;
        for (int i = 0; i < path.length; i++) {
            String p = path[i];
            dir = new File(dir.getAbsolutePath().replace("/", "\\") + "\\" + p);
            if(!dir.exists()) {
                dir.mkdir();
                if(i != path.length - 1)
                    JWHUtils.write(new File(dir, "directory"), "");
            }
            if(!dir.isDirectory())
                throw new NotDirectoryException("This is not a directory: " + dir.getAbsolutePath());

            if(!new File(dir.getAbsolutePath() + "\\" + "directory").exists()) {
                if(i == path.length - 1) {
                    JWHUtils.write(new File(dir.getAbsolutePath() + "\\" + "content"), value);
                } else {
                    throw new RuntimeException("This is a directory but this directory is not a directory.");
                }
            }
        }
    }
    public void setDatabaseValueCustom(@NotNull String path, String value) throws IOException {
        setDatabaseValueCustom(path.split("\\."), value);
    }
    public void setDatabaseDirectory(File directory) {
        this.dbDir = directory;
    }
    public void setDatabaseValue(@NotNull String path, Object value) throws IOException {
        setDatabaseValue(path.split("\\."), value);
    }
    public void setDatabaseValue(String[] path, Object value) throws IOException {
        setDatabaseValueCustom(path, toString(value));
    }
    @Nullable
    public Object getDatabaseValue(@NotNull String path) throws IOException, ParseException {
        return getDatabaseValue(path.split("\\."));
    }
    @Nullable
    public String getDatabaseStringValue(@NotNull String path) throws IOException, ParseException {
        return (String) getDatabaseValue(path.split("\\."));
    }
    @Nullable
    public Object getDatabaseValue(String[] path) throws IOException, ParseException {
        return toObject(getDatabaseValueCustom(path));
    }
    private @NotNull String toString(Object obj) {
        if(obj == null)
            return "NULL";
        if(obj instanceof String str) {
            return "STRING" + ':' + str;
        } else if(obj instanceof Boolean b) {
            return "BOOLEAN" + ':' + (b ? "1" : "0");
        } else if(obj instanceof Byte b) {
            return "BYTE" + ':' + b;
        } else if(obj instanceof Short s) {
            return "SHORT" + ':' + s;
        } else if(obj instanceof Integer i) {
            return "INTEGER" + ':' + i;
        } else if(obj instanceof Long l) {
            return "LONG" + ':' + l;
        } else if(obj instanceof Float f) {
            return "FLOAT" + ':' + f;
        } else if(obj instanceof Double d) {
            return "DOUBLE" + ':' + d;
        } else {
            // It's not reversible
            return obj.getClass().getName() + ':' + obj.toString();
        }
    }
    private @Nullable Object toObject(@NotNull String str) throws ParseException {
        if(str.equals("NULL"))
            return null;
        if(str.startsWith("STRING:")) {
            return str.substring(7);
        } else if(str.startsWith("BOOLEAN:")) {
            return str.substring(8).equals("1");
        } else if(str.startsWith("BYTE:")) {
            return Byte.valueOf(str.substring(5));
        } else if(str.startsWith("SHORT:")) {
            return Short.valueOf(str.substring(6));
        } else if(str.startsWith("INTEGER:")) {
            return Integer.valueOf(str.substring(8));
        } else if(str.startsWith("LONG:")) {
            return Long.valueOf(str.substring(5));
        } else if(str.startsWith("FLOAT:")) {
            return Float.valueOf(str.substring(7));
        } else if(str.startsWith("DOUBLE:")) {
            return Double.valueOf(str.substring(7));
        } else {
            throw new ParseException("Cannot parse.");
        }
    }
}
