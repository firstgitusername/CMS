package com.xuecheng.filesystem.service;

import com.xuecheng.filesystem.dao.FileSystemRespository;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemService {
    private static final Logger LOG= LoggerFactory.getLogger(FileSystemService.class);

    @Autowired
    private FileSystemRespository fileSystemRespository;

    @Value("${xuecheng.fastdfs.tracker_servers}")
    private String tracker_servers;

    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    private int connect_timeout_in_seconds;

    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    private int network_timeout_in_seconds;

    @Value("${xuecheng.fastdfs.charset}")
    private String charset;
    public UploadFileResult upload(FileSystem fileSystem,MultipartFile multipartFile){
        if(multipartFile.isEmpty()){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        try {
            String fileId = this.fdfs_Upload(multipartFile);
            fileSystem.setFileId(fileId);
            fileSystem.setFilePath(fileId);
            FileSystem save = fileSystemRespository.save(fileSystem);
            return new UploadFileResult(CommonCode.SUCCESS,save);
        }catch(Exception e){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
        return null;

    }

    public void initFastDFS(){
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);
        }catch(Exception e){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
    }
    public String fdfs_Upload(MultipartFile multipartFile) throws Exception{
            this.initFastDFS();
            TrackerClient tc = new TrackerClient();
            //连接tracker Server
            TrackerServer ts = tc.getConnection();
            //获取一个storage server
            StorageServer ss = tc.getStoreStorage(ts);
            //创建一个storage存储客户端
            StorageClient1 sc1 = new StorageClient1(ts, ss);
            byte[] bytes = multipartFile.getBytes();
            String fileName = multipartFile.getOriginalFilename();
            String extra= StringUtils.isEmpty(fileName)?"png":fileName.substring(fileName.lastIndexOf(".")+1);
            String s = sc1.upload_file1(bytes, extra, null);
            return s;
    }

}
