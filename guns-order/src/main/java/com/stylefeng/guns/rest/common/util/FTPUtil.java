package com.stylefeng.guns.rest.common.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
            ftpClient.logout();
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
        }
        return  null;
    }
}
