import java.text.SimpleDateFormat;
import java.util.Date;

public class Console {
    String[] Logs = new String[0];
    public Console() {

    }
    public String[] logs() {
        return Logs;
    }
    public void logs(String[] l) {
        Logs = l;
    }

    public void write(int b) {
        String[] temp = new String[Logs.length + 1];
        for(int i = 0; i < Logs.length; i++) {
            temp[i] = Logs[i];
        }
        temp[temp.length - 1] = String.valueOf(b);
        System.out.print(String.valueOf(b));
    }
    public void print(String from, String message) {
        String msg = String.format(
                "[%s] [%s] %s",
                new SimpleDateFormat("yyyy-MM-dd H:mm:ss").format(new Date()),
                from, message
        );
        String[] temp = new String[Logs.length + 1];
        for(int i = 0; i < Logs.length; i++) {
            temp[i] = Logs[i];
        }
        temp[temp.length - 1] = msg;
        System.out.print(msg);
    }
    public void println(String from, String message) {
        String msg = String.format(
                "[%s] [%s] %s",
                new SimpleDateFormat("yyyy-MM-dd H:mm:ss").format(new Date()),
                from, message
        );
        String[] temp = new String[Logs.length + 1];
        for(int i = 0; i < Logs.length; i++) {
            temp[i] = Logs[i];
        }
        temp[temp.length - 1] = msg + "\n";
        System.out.println(msg);
    }
}
