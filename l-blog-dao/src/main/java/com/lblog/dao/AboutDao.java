package com.lblog.dao;

import com.lblog.domain.About;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AboutDao {
    Integer editAbout(About about);

    About getAboutDetail(@Param("id") Long aboutId);
}
