import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if(args.length == 0) {
            System.out.println("HI GUY!");
        } else {
            System.out.println(Arrays.toString(args));
            return;
        }
        Method Method = Main.class.getMethod("main", String[].class);
        Object instance = new Object();

        Method.invoke(null, (Object) new String[]{"Abc", "def"});
    }
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("HI GUY!");
        } else {
            System.out.println(Arrays.toString(args));
            return;
        }
        Main.main(new String[]{"Abc", "def"});
    }
}
