package Logger;

public class LoggersCtrl
{
    public static final Logger systemLogger = new Logger("SYSTEM", Logger.ANSI_RED, Logger.ANSI_RESET);
    public static final Logger gameLogger = new Logger("GAME", Logger.ANSI_GREEN, Logger.ANSI_RESET);

}
