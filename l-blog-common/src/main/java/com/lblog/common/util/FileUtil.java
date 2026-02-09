package com.lblog.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

public class FileUtil {
    /**
     * 校验文件格式
     * @param file 上传文件
     * @param allowContentType 允许上传的文件格式
     * */
    public static Boolean validateContentType(MultipartFile file, List<String> allowContentType){
        if(allowContentType == null || allowContentType.isEmpty()){
            return false;
        }

        String contentType = file.getContentType();
        return allowContentType.contains(contentType);
    }

    /**
     * 校验文件大小（单位MB）
     * @param file 上传文件
     * @param fileSize 允许上传的文件大小
     * */
    public static Boolean validateFileSize(MultipartFile file, Integer fileSize){
        if(file.getSize() > (fileSize*1024*1024)){
            return false;
        }

        return true;
    }

    /**
     * 获取文件后缀
     * @param file 上传文件
     * @return 文件后缀
     * */
    public static String getExtension(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        if(originalFilename == null || !originalFilename.contains(".")){
            return "";
        }
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        return extension;
    }

    /**
     * 创建文件夹
     * @param dirPath 文件夹路径
     * @return 文件夹路径
     * */
    public static String createDirectory(String dirPath){
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        return dirPath;
    }

    /**
     * 上传文件到服务器
     * @param file 上传文件
     * @param dirPath 文件夹路径
     * @return 文件完整链接
     * */
    public static String uploadFile(MultipartFile file, String dirPath) throws IOException {
        if(file == null || file.isEmpty()){
            throw new IOException("文件不能为空！");
        }

        String extension = getExtension(file);
        if(StringUtils.isBlank(extension)){
            throw new IOException("文件后缀名不能为空！");
        }

        //转绝对路径
        File isAbsoluteDir = new File(dirPath);
        String absolutePath = "";
        if(!isAbsoluteDir.isAbsolute()){
            isAbsoluteDir = new File(isAbsoluteDir.getAbsolutePath());
        }
        absolutePath = isAbsoluteDir.getPath();

        //创建文件夹
        createDirectory(absolutePath);

        //生成文件名
        Long updateTime = Instant.now().getEpochSecond();
        String fileName = updateTime + extension;

        //上传文件
        File fileResult = new File(absolutePath, fileName);
        file.transferTo(fileResult);

        return dirPath + fileName;
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     * */
    public static Boolean deleteFile(String filePath){
        if(StringUtils.isBlank(filePath)){
            return false;
        }

        File file = new File(filePath);
        if(!file.exists() || !file.isFile()){
            return false;
        }

        return file.delete();
    }
}
