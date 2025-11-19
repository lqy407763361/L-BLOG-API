package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.dao.AboutDao;
import com.lblog.domain.About;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AboutService {
    private static final Long ABOUT_ID = 1L;

    @Autowired
    private AboutDao aboutDao;

    @Transactional
    public void editAbout(About about){
        //内容过滤
        String content = "";
        if(!StringUtils.isBlank(about.getContent())){
            content = about.getContent().trim();
        }

        //更改单页信息
        Integer status = about.getStatus();
        Long editTime = Instant.now().getEpochSecond();
        about.setId(ABOUT_ID);
        about.setContent(content);
        about.setStatus(status);
        about.setEditTime(editTime);
        Integer returnRow = aboutDao.editAbout(about);
        if((returnRow == null) || (returnRow == 0)){
            throw new ReturnException("编辑失败！");
        }
    }

    //获取单页详情
    public About getAboutDetail(){

        return aboutDao.getAboutDetail(ABOUT_ID);
    }
}
