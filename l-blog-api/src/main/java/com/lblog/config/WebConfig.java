package com.lblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //获取项目根目录
        String projectPath = System.getProperty("user.dir");
        //构建完整上传目录路径
        String uploadPath = "file:" + projectPath + "/uploads/";
        //映射URL路径到物理路径
        registry.addResourceHandler("/uploads/**").addResourceLocations(uploadPath);
    }
}
