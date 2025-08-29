package com.lblog.api;

import com.lblog.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestApi {

    @Autowired
    private TestService testService;

    @GetMapping("/testQuery")
    public Map<String, Object> testQuery(Long id){
        return testService.testQuery(id);
    }
}
