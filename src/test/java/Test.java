import com.kronae.jwh.*;
import com.kronae.jwh.exception.ParseException;
import com.kronae.jwh.status.JWHStatusCode;
import com.kronae.jwh.util.api.JWHApi;
import com.kronae.jwh.util.api.JWHDatabaseApi;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        JWHDatabaseApi db;
        try {
            db = new JWHDatabaseApi(new File("C:\\Users\\mcnam\\OneDrive\\바탕 화면\\DB"));
        } catch (NotDirectoryException e) {
            throw new RuntimeException(e);
        }

        JWH web = new JWH(80);
        web.addHandler((req, res) -> {
            System.out.println(req.getAddress().getHostName() + ": " + req.getURI().getPath() + ": " + req.readData());
            res.write("asdf");
            res.header().add("Content-Type", "text/html");
            res.end(JWHStatusCode.SUCCESS);
        });
        web.addApi(db);
        web.start();
        db.setDatabaseValue("user.kronae", 1239031L);
//        System.out.println(db.get("user.kronae"));
//        System.err.println(obj.getClass());
    }
}
