package com.nicommons.file;


import com.nicommons.exception.FtpException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 * ftp上传下载文件工具
 *
 * @author feng
 * @date 2018-08-14 19:54
 **/
public class FtpSession {
    private FTPClient client;
    private String userName;
    private String passWord;
    private String host;
    private int port;

    public FtpSession(String userName, String passWord, String host, int port) {
        this.client = new FTPClient();
        this.userName = userName;
        this.passWord = passWord;
        this.host = host;
        this.port = port;
    }

    public FtpSession(){
        this.client = new FTPClient();
        this.port = 21;
        this.host = "http://127.0.0.1";
    }

    public FTPClient getClient() {
        return client;

    }

    public void setClient(FTPClient client) {
        this.client = client;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    /**
     * 打开ftp连接，包括连接和登录
     * @return true表示登录成功
     * @throws FtpException ftp异常
     */
    private boolean open() throws FtpException {
        boolean stat = false;
        try {
            if (!client.isConnected()){
                client.connect(host, port);
            }
            stat = client.login(userName, passWord);
            int reply = client.getReply();
            if (!FTPReply.isProtectedReplyCode(reply)){
                close();
                stat = false;
            }
        } catch (SocketException e) {
            throw new FtpException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stat;
    }

    /**
     * 关闭ftp连接
     * @throws FtpException ftp异常
     */
    private void close() throws FtpException{
        try {
            if (client != null){
                if (client.isConnected()){
                    client.logout();
                    client.disconnect();
                }
            }
        } catch (IOException e){
            throw new FtpException(e);
        }
    }

    /**
     * 创建目录
     * @param dir 目录路径
     * @return 创建结果
     * @throws FtpException ftp异常
     */
    private boolean mkdir(String dir) throws FtpException{
        boolean stat = false;
        try {
            client.changeToParentDirectory();
            client.makeDirectory(dir);
            stat = true;
        } catch (IOException e) {
            throw new FtpException(e);
        }
        return stat;
    }

    /**
     * 打开目录
     * @param dir 目录路径
     * @return 打开结果
     * @throws FtpException ftp异常
     */
    private boolean openDir(String dir) throws FtpException{
        boolean stat = false;
        try {
            String[] dirs = dir.split("/");
            if (dirs.length == 0){
                return client.changeWorkingDirectory(dir);
            }
            stat = client.changeToParentDirectory();
            for (String dirss : dirs){
                stat = stat && client.changeWorkingDirectory(dirss);
            }
        } catch (IOException e) {
            throw new FtpException(e);
        }
        return stat;
    }

    /**
     * 上传文件
     * @param in 文件IO流
     * @param remotePath 远程文件路径
     * @param fileName 要保存的文件名
     * @return 上传结果
     * @throws FtpException ftp异常
     */
    private boolean upload(InputStream in, String remotePath, String fileName) throws FtpException{
        boolean stat = false;
        try {
            openDir(remotePath);
            client.setBufferSize(2048);
            client.setControlEncoding("UTF-8");
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            stat = client.storeFile(fileName, in);
            in.close();
        } catch (Exception e) {
            throw new FtpException(e);
        }
        return stat;
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @param remotePath 远程文件路径
     * @param fileName 要保存的文件名
     * @return 上传结果
     * @throws FtpException ftp异常
     */
    private boolean upload(File file, String remotePath, String fileName) throws FtpException{
        boolean stat = false;
        try {
            InputStream in = new FileInputStream(file);
            stat = upload(in, remotePath, fileName);
        }catch (Exception e){
            throw new FtpException(e);
        }
        return stat;
    }

    /**
     * 上传文件
     * @param localPath 本地文件路径
     * @param remotePath 远程文件路径
     * @param fileName 要保存的文件名
     * @return 上传结果
     * @throws FtpException ftp异常
     */
    private boolean upload(String localPath, String remotePath, String fileName) throws FtpException{
        boolean stat = false;
        try {
            File file = new File(localPath);
            stat = upload(file, remotePath, fileName);
        }catch (Exception e){
            throw new FtpException(e);
        }
        return stat;
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @param remoteFile 远程文件路径，包含文件名
     * @return 文件上传结果
     * @throws FtpException ftp
     */
    private boolean upload(File file, String remoteFile) throws FtpException{
        boolean stat = false;
        String fileName;
        String filePath;
        String[] dirs = remoteFile.split("/");
        if (dirs.length == 1){
            fileName = remoteFile;
            filePath = remoteFile;
        }
        fileName = dirs[dirs.length - 1];
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < dirs.length - 1; ){
            builder.append(dirs[i]);
        }
        filePath = builder.toString();

        try {
            stat = upload(file, filePath, fileName);
        }catch (Exception e){
            throw new FtpException(e);
        }
        return stat;
    }

    /**
     * 文件上传
     * @param localFile 本地文件全路径
     * @param remoteFile 远程文件全路径
     * @return 上传结果
     * @throws FtpException ftp异常
     */
    private boolean upload(String localFile, String remoteFile) throws FtpException{
        boolean stat = false;
        try {
            File file = new File(localFile);
            stat = upload(file, remoteFile);
        }catch (Exception e){

        }
        return stat;
    }

    /**
     * 简易上传文件，只需实例化后直接调用即可
     * @param file 文件对象
     * @param remoteFile 远程文件全路径
     * @return 上传结果
     * @throws FtpException ftp异常
     */
    private boolean easyUpload(File file, String remoteFile) throws FtpException{
        boolean stat = false;
        try {
            if (open()){
                stat = upload(file, remoteFile);
                close();
            }
        }catch (Exception e){
            throw new FtpException(e);
        }

        return stat;
    }

    /**
     * 简易上传文件，只需实例化后直接调用即可
     * @param localFile 本地文件路径
     * @param remoteFile 远程文件全路径
     * @return 上传结果
     * @throws FtpException ftp异常
     */
    private boolean easyUpload(String localFile, String remoteFile) throws FtpException{
        boolean stat = false;
        try {
            if (open()){
                stat = upload(localFile, remoteFile);
                close();
            }
        }catch (Exception e){
            throw new FtpException(e);
        }

        return stat;
    }
}
