package util.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import properter.Config;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * ClassName: FileUtils
 * Description: 文件下载工具类
 * date: 2020/7/15 16:11
 * @author Mr.zhang
 */
public class FileUtil {


    public static Logger getLog() {
        return log;
    }

    private static final Logger log  = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取操作系统目录
     * @param winPath windows路径
     * @param linuxPath linux路径
     */
    public static String getSysPath(String winPath,String linuxPath) {
        StringBuilder sb = new StringBuilder();
        if (SystemUtils.OS_NAME.toLowerCase().startsWith(Config.SYS_NAME_WIN)) {
            sb.append(FilenameUtils.normalizeNoEndSeparator(winPath));
        } else {
            sb.append(FilenameUtils.normalizeNoEndSeparator(winPath));
        }
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * 创建文件夹
     * @param dirName 文件夹名字
     */
    public static void creatDir(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            if (!dirName.endsWith(File.separator)) {
                dirName = dirName + File.separator;
            }
            if (dir.mkdirs()) {
                log.info("创建文件夹成功");
            } else {
                log.info("创建文件夹失败");
            }
        }
    }


    /**
     * 复制文件
     * @param fromFile 源文件
     * @param toPath 复制到路径
     */
    public static boolean copyFile(File fromFile,String toPath){
        creatDir(FilenameUtils.getFullPath(toPath));
        try {
            FileUtils.copyFile(fromFile,new File(toPath));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 源文件不存在则创建文件
     * @param fileName 文件名字
     */
    public static File creatFile(String fileName) throws IOException {
        File file = new File(fileName);
        boolean newFile = false;
        if (!file.exists()) {
            newFile = file.createNewFile();
        }
        return file;
    }

    /**
     * 创建新文件
     * @param fileName 文件名称
     */
    public static boolean creatFileNew(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            boolean delete = file.delete();
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据旧文件路径删除旧文件
     * @param oldPath 旧文件地址
     */
    public static boolean delOldFile(String oldPath) {
        File old = new File(oldPath);
        boolean delete = false;
        if (old.exists()) {
            delete = old.delete();
        }
        return  delete;
    }

    /**
     * 删除多级文件夹
     * @param dir 要删除的顶级目录
     */
    public static boolean delDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                boolean success = delDir(new File(dir, child));
                if (!success) return false;
            }
        }
        return dir.delete();
    }


    /**
     * 添加日志到本地操作文件中
     * @param context 需要增加的日志内容
     */
//    public static void customerAddLog(String context) {
//        FileOutputStream out = null;
//        try {
//            String sysPath = getSysPath(logProperties.getWinPath(), logProperties.getLinuxPath());
//            //日志内容中的时间具体到时分秒
//            String strByDate = DateUtil.getStrByDate(new Date(), DateConstant.YYYY_MM_DD_HH_MM_SS);
//            //日志文件命名为年月日
//            String date = DateUtil.getStrByDate(new Date(), DateConstant.YYYY_MM_DD);
//            StringBuilder sb = null;
//            File file = new File(sysPath);
//            File result = null;
//            if (!file.isDirectory()) file.mkdirs();
//            result = creatFile(sysPath + date + ".out");
//            sb = new StringBuilder();
//            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
//            sb.append("[").append(strByDate).append("]: ");
//            sb.append(stackTraceElement.getClassName()).append("----------");
//            sb.append(stackTraceElement.getMethodName()).append(": ").append(stackTraceElement.getLineNumber());
//            sb.append("============>").append(context).append("\r\n");
//            out = new FileOutputStream(result, true);
//            out.write(sb.toString().getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                IOUtils.close(out);
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        }
//    }

}
