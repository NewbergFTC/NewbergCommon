package us.or.k12.newberg.newbergcommon;

import android.os.Environment;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;

public class LogUtil
{
    public static String LOG_PATH;

    public static void InitXLog()
    {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(LogLevel.ALL)
                .tag("BULLET")
                .t()
                .st(4)
                .b()
                .build();

        LOG_PATH = Environment.getExternalStorageDirectory() + "/./bullet/";

        Printer androidPrinter = new AndroidPrinter();
        Printer filePrinter = new FilePrinter.Builder(LOG_PATH)
                .fileNameGenerator(new DateFileNameGenerator())
                .backupStrategy(new NeverBackupStrategy())
                .build();

        XLog.init(config, androidPrinter, filePrinter);

        XLog.d("Path: " + LOG_PATH);
    }
}
