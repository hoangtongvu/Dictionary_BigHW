package Logger;

public class Logger
{
    private final String prefix;
    private final String prefixColor;
    private final String logColor;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public Logger(String rawPrefix, String prefixColor, String logColor)
    {
        this.prefix = this.getPrefix(rawPrefix);
        this.logColor = logColor;
        this.prefixColor = prefixColor;
    }

    private String getPrefix(String rawPrefix)
    {
        return "[" + rawPrefix + "]";
    }

    public void Log(String log)
    {
        System.out.print(this.prefixColor + prefix + " ");
        System.out.print(this.logColor + log + ANSI_RESET);
    }

    public void Log(String raw2ndPrefix, String log)
    {
        String secondPrefix = this.getPrefix(raw2ndPrefix);
        System.out.print(this.prefixColor + prefix + secondPrefix + " ");
        System.out.print(this.logColor + log + ANSI_RESET);
    }


}
