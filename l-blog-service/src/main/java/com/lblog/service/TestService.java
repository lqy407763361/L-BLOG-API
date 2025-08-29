package com.lblog.service;

import com.lblog.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestService {

    @Autowired
    private TestDao testDao;

    public Map<String, Object> testQuery(Long id){
        return testDao.testQuery(id);
    }
}
