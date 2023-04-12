public class Command {
    static Action run(String c, Console console) {
        if(c.equalsIgnoreCase("stop")) {
            return Action.ACT_SERVER_STOP;
        } else if(c.equalsIgnoreCase("open")) {
            return Action.ACT_SERVER_OPEN;
        } else if(c.equalsIgnoreCase("exit")) {
            return Action.ACT_SYSTEM_EXIT;
        }
        return Action.ERR_UNKNOWN_COMMAND;
    }
}
