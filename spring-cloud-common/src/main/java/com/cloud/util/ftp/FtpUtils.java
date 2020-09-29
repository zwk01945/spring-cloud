package com.cloud.util.ftp;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : FtpUtils.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/9/7 9:38                       *
 *                                                            *
 *         Last Update : 2020/9/7 9:38                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbib.io.ftp.client.FTPClient;
import org.xbib.io.ftp.client.FTPFile;
import org.xbib.io.ftp.client.FTPReply;
import java.io.*;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;


/**
 * 简单操作FTP工具类 ,此工具类支持中文文件名，不支持中文目录
 * 如果需要支持中文目录，需要 new String(path.getBytes("UTF-8"),"ISO-8859-1") 对目录进行转码
 *
 */
public class FtpUtils {

    private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

    public static FTPClient getClient(String ftpHost, int ftpPort,
                               String ftpUserName, String ftpPassword) {
        FTPClient client = null;
        try {
            client = new FTPClient();
            client.connect(ftpHost,ftpPort);
            client.login(ftpUserName,ftpPassword);
            client.setConnectTimeout(60000);
            client.setControlEncoding("UTF-8");
            if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
                logger.info("未连接到FTP，用户名或密码错误");
                client.disconnect();
            } else {
                logger.info("连接到FTP");
            }
        }catch (SocketException e){
            e.printStackTrace();
            logger.info("FTP的IP地址可能错误，请正确配置");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("FTP的端口错误,请正确配置");
        }
        return client;
    }



    public static boolean closeClient(FTPClient client) {
        boolean closed = false;
        try {
            closed =  client.logout();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("FTP关闭失败");
        }finally {
            if (client.isConnected()) {
                try {
                    client.disconnect();
                } catch (IOException ioe) {
                    logger.error("FTP关闭失败");
                }
            }
        }
        return closed;
    }


    /**
     *
     * @param ftp ftp客户端
     * @param filePath 服务器上文件路径
     * @param fileName 服务器上文件名称
     * @param downPath 服务器下载到本地路径
     */
    public static boolean downloadFile(FTPClient ftp, String filePath, String fileName,
                             String downPath) {

        boolean flag = false;

        try {
            logger.info("切换到服务器目录下");
            ftp.changeWorkingDirectory(filePath);
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            FTPFile[] ftpFiles = ftp.listFiles(filePath);
            logger.info("服务器下文件的数量为{}",ftpFiles.length);
            System.out.println(Arrays.stream(ftpFiles).filter(file -> file.getName().equals(fileName)).count());
            Optional<FTPFile> first = Arrays.stream(ftpFiles).filter(file -> file.getName().equals(fileName)).findFirst();
            if (first.isPresent()) {
                FTPFile file = first.get();
                System.out.println(file.getName());
                File downFile = new File(downPath + File.separator
                        + file.getName());
                OutputStream out = new FileOutputStream(downFile);
                flag = ftp.retrieveFile(new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1), out);
                // 下载成功删除文件,看项目需求
                // ftp.deleteFile(new String(fileName.getBytes("UTF-8"),"ISO-8859-1"));
//                out.flush();
//                out.close();
                if(flag){
                    logger.info("下载成功");
                }else{
                    logger.error("下载失败");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("下载失败");
        }
        return flag;
    }


    /**
     * FTP文件上传工具类
     * @param ftp 服务器连接客户端
     * @param filePath 上传文件的所在目录
     * @param ftpPath 服务器上的存放路径
     * @return
     */
    public static boolean uploadFile(FTPClient ftp,String filePath,String ftpPath){
        boolean flag = false;
        InputStream in = null;
        try {
            // 设置PassiveMode传输
            ftp.enterLocalPassiveMode();
            //设置二进制传输，使用BINARY_FILE_TYPE，ASC容易造成文件损坏
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //判断FPT目标文件夹时候存在不存在则创建
            if(!ftp.changeWorkingDirectory(ftpPath)){
                ftp.makeDirectory(ftpPath);
            }
            //跳转目标目录
            ftp.changeWorkingDirectory(ftpPath);
            //上传文件
            File file = new File(filePath);
            in = new FileInputStream(file);
            String tempName = ftpPath+File.separator+file.getName();
            flag = ftp.storeFile(new String (tempName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1),in);
            if(flag){
                logger.info("上传成功");
            }else{
                logger.error("上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传失败");
        }finally{
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * FPT上文件的复制
     * @param ftp  FTPClient对象
     * @param olePath 原文件地址
     * @param newPath 新保存地址
     * @param fileName 文件名
     * @return
     */
    public static boolean copyFile(FTPClient ftp, String olePath, String newPath,String fileName) {
        boolean flag = false;

        try {
            // 跳转到文件目录
            ftp.changeWorkingDirectory(olePath);
            //设置连接模式，不设置会获取为空
            ftp.enterLocalPassiveMode();
            // 获取目录下文件集合
            FTPFile[] files = ftp.listFiles();
            ByteArrayInputStream  in = null;
            ByteArrayOutputStream out = null;

            Optional<FTPFile> first = Arrays.stream(files).filter(file -> file.getName().equals(fileName)).findFirst();
            if (first.isPresent()) {
                FTPFile file = first.get();
                //读取文件，使用下载文件的方法把文件写入内存,绑定到out流上
                out = new ByteArrayOutputStream();
                ftp.retrieveFile(new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1), out);
                in = new ByteArrayInputStream(out.toByteArray());
                //创建新目录
                ftp.makeDirectory(newPath);
                //文件复制，先读，再写
                //二进制
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                flag = ftp.storeFile(newPath+File.separator+(new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)),in);
                out.flush();
                out.close();
                in.close();
                if(flag){
                    logger.info("转存成功");
                }else{
                    logger.error("复制失败");
                }
            } else {
                logger.info("服务器中不存在该文件");
            }

        } catch (Exception e) {
            logger.error("复制失败");
        }
        return flag;
    }

    /**
     * 实现文件的移动，这里做的是一个文件夹下的所有内容移动到新的文件，
     * 如果要做指定文件移动，加个判断判断文件名
     * 如果不需要移动，只是需要文件重命名，可以使用ftp.rename(oleName,newName)
     * @param ftp
     * @param oldPath
     * @param newPath
     * @return
     */
    public boolean moveFile(FTPClient ftp,String oldPath,String newPath){
        boolean flag = false;

        try {
            ftp.changeWorkingDirectory(oldPath);
            ftp.enterLocalPassiveMode();
            //获取文件数组
            FTPFile[] files = ftp.listFiles();
            //新文件夹不存在则创建
            if(!ftp.changeWorkingDirectory(newPath)){
                ftp.makeDirectory(newPath);
            }
            //回到原有工作目录
            ftp.changeWorkingDirectory(oldPath);
            for (FTPFile file : files) {

                //转存目录
                flag = ftp.rename(new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1), newPath+File.separator+new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                if(flag){
                    logger.info(file.getName()+"移动成功");
                }else{
                    logger.error(file.getName()+"移动失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("移动文件失败");
        }
        return flag;
    }

    /**
     * 删除FTP上指定文件夹下文件及其子文件方法，添加了对中文目录的支持
     * @param ftp FTPClient对象
     * @param FtpFolder 需要删除的文件夹
     * @return
     */
    public boolean deleteByFolder(FTPClient ftp,String FtpFolder){
        boolean flag = false;
        try {
            ftp.changeWorkingDirectory(new String(FtpFolder.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            ftp.enterLocalPassiveMode();
            FTPFile[] files = ftp.listFiles();
            for (FTPFile file : files) {
                //判断为文件则删除
                if(file.isFile()){
                    ftp.deleteFile(new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                }
                //判断是文件夹
                if(file.isDirectory()){
                    String childPath = FtpFolder + File.separator+file.getName();
                    //递归删除子文件夹
                    deleteByFolder(ftp,childPath);
                }
            }
            //循环完成后删除文件夹
            flag = ftp.removeDirectory(new String(FtpFolder.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            if(flag){
                logger.info(FtpFolder+"文件夹删除成功");
            }else{
                logger.error(FtpFolder+"文件夹删除成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除失败");
        }
        return flag;

    }

    /**
     * 遍历解析文件夹下所有文件
     * @param folderPath 需要解析的的文件夹
     * @param ftp FTPClient对象
     * @return
     */
    public boolean readFileByFolder(FTPClient ftp,String folderPath){
        boolean flage = false;
        try {
            ftp.changeWorkingDirectory(new String(folderPath.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            //设置FTP连接模式
            ftp.enterLocalPassiveMode();
            //获取指定目录下文件文件对象集合
            FTPFile files[] = ftp.listFiles();
            InputStream in = null;
            BufferedReader reader = null;
            for (FTPFile file : files) {
                //判断为txt文件则解析
                if(file.isFile()){
                    String fileName = file.getName();
                    if(fileName.endsWith(".txt")){
                        in = ftp.retrieveFileStream(new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                        reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                        String temp;
                        StringBuilder buffer = new StringBuilder();
                        while((temp = reader.readLine())!=null){
                            buffer.append(temp);
                        }
                        if(reader!=null){
                            reader.close();
                        }
                        if(in!=null){
                            in.close();
                        }
                        //ftp.retrieveFileStream使用了流，需要释放一下，不然会返回空指针
                        ftp.completePendingCommand();
                        //这里就把一个txt文件完整解析成了个字符串，就可以调用实际需要操作的方法
                        System.out.println(buffer.toString());
                    }
                }
                //判断为文件夹，递归
                if(file.isDirectory()){
                    String path = folderPath+File.separator+file.getName();
                    readFileByFolder(ftp, path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("文件解析失败");
        }

        return flage;

    }

    public static void main(String[] args) throws IOException {
        FTPClient test = FtpUtils.getClient("ftp.apnic.net", 21, "anonymous", "");
        FtpUtils.downloadFile(test,"/public/apnic/whois","apnic.db.inetnum.gz","D:\\ftpdownload");
//        FtpUtils.uploadFile(test,"D:\\ftpdownload\\niubi.jpg","\\");
//        FtpUtils.copyFile(test,"\\","\\image\\","a.png");
    }

}
