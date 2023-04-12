import com.kronae.jwh.*;
import com.kronae.jwh.request.JWHRequest;
import com.kronae.jwh.request.JWHRequestHandler;
import com.kronae.jwh.response.JWHResponse;
import com.kronae.jwh.status.JWHStatusCode;
import com.kronae.jwh.util.api.JWHLoginApi;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        JWH web = new JWH(80);
        JWHLoginApi loginApi = web.addApi(new JWHLoginApi());
        web.addHandler(new JWHRequestHandler() {
            @Override
            public void handle(JWHRequest req, JWHResponse res) throws IOException {
                System.out.println(req.getAddress().getHostName() + ": " + req.getURI().getPath() + ": " + req.readData());
                res.write("asdf");
                res.header().add("Content-Type", "text/html");
                res.end(JWHStatusCode.SUCCESS);
            }
        });
        web.start();
    }
}
