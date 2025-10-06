package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.dao.AboutDao;
import com.lblog.domain.About;
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
        //更改单页信息
        String content = about.getContent().trim();
        Integer status = about.getStatus();
        Long editTime = Instant.now().toEpochMilli();
        about.setId(ABOUT_ID);
        about.setContent(content);
        about.setStatus(status);
        about.setEditTime(editTime);
        Integer returnRow = aboutDao.editAbout(about);
        if((returnRow == null) || (returnRow == 0)){
            throw new ReturnException("编辑失败！");
        }
    }

    //获取管理员详情
    public About getAboutDetail(){

        return aboutDao.getAboutDetail(ABOUT_ID);
    }
}
