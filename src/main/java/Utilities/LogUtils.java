package Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {

    public static String LOGS_PATH = "test-outputs/Logs/";

    public static void trace_Log (String message){
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .trace(message);
    }

    public static void debug_Log (String message){
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .debug(message);
    }

    public static void info_Log (String message){
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .info(message);
    }

    public static void warn_Log (String message){
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .warn(message);
    }

    public static void error_Log (String message){
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .error(message);
    }

    public static void fatal_Log (String message){
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .fatal(message);
    }



}
