package com.stylefeng.guns.rest.common.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.*;

/**
 * Created by Su on 2019/4/30.
 * FTP文件服务器管理器
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "ftp")
public class FTPUtil {

    private String hostName;

    private Integer port;

    private String userName;

    private String password;

    private String uploadPath;

    private FTPClient ftpClient = null;

    private void initFTPClient(){
        try{
            ftpClient = new FTPClient();
            ftpClient.setControlEncoding("utf-8");
            ftpClient.setConnectTimeout(1000*20);
            ftpClient.connect(hostName,port);
            ftpClient.login(userName, password);
            log.info("登录成功");
        }catch (Exception e){
            log.error("初始化失败", e);
        }
    }

    public String getFileStrByAddress(String fileAddress){
        BufferedReader bufferedReader = null;
        initFTPClient();
        try{
            bufferedReader = new BufferedReader(
                    new InputStreamReader(ftpClient.retrieveFileStream(fileAddress))
            );
            StringBuffer fileStr = new StringBuffer();
            while(true){
                 String lineStr = bufferedReader.readLine();
                if (StringUtils.isEmpty(lineStr))
                    break;
                fileStr.append(lineStr);
            }
            return  fileStr.toString();
        }catch (Exception e){
            log.error("获取文件信息失败",e);
        }finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();

            } catch (IOException e) {
                log.error("关闭读取流失败",e);
            }
            try {
                ftpClient.logout();
            } catch (IOException e) {
                log.error("FTP服务器退出失败",e);
            }
        }
        return  null;
    }


    public boolean uploadFile(String fileName, File file){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);

            initFTPClient();
            ftpClient.setControlEncoding("utf-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            boolean isSuccess = ftpClient.changeWorkingDirectory(this.getUploadPath());
            log.info("上传路径：{},改变路径{}",this.getUploadPath(),isSuccess);
            ftpClient.storeFile(fileName, fileInputStream);
            return Boolean.TRUE;
        }catch (Exception e){
            log.error("上传文件失败",e);
        }finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            }catch (Exception e){
                log.error("关闭上传流失败",e);
            }

            try {
                ftpClient.logout();
            } catch (IOException e) {
                log.error("FTP服务器退出失败",e);
            }
        }
        return Boolean.FALSE;
    }
}
