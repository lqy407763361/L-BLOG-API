package com.lblog.api;

import com.lblog.api.auth.UserIdentityAuth;
import com.lblog.common.util.GetClientIpUtil;
import com.lblog.common.util.JsonResponseUtil;
import com.lblog.common.util.PageResultUtil;
import com.lblog.domain.User;
import com.lblog.dto.UserVisitRecordDto;
import com.lblog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserApi {

    @Autowired
    private UserIdentityAuth userIdentityAuth;

    @Autowired
    private UserService userService;

    //登录
    @PostMapping("/login")
    public JsonResponseUtil<Map<String, Object>> login(User user, HttpServletRequest request){
        String userIp = GetClientIpUtil.getClientIp(request);
        Map<String, Object> tokenMap = userService.login(user, userIp);

        return JsonResponseUtil.success(tokenMap);
    }

    //注册
    @PostMapping("/register")
    public JsonResponseUtil<Map<String, Object>> register(User user, HttpServletRequest request){
        String userIp = GetClientIpUtil.getClientIp(request);
        Map<String, Object> tokenMap = userService.register(user, userIp);

        return JsonResponseUtil.success(tokenMap);
    }

    //退出登录
    @PostMapping("/loginOut")
    public JsonResponseUtil<String> loginOut(HttpServletRequest request){
        Long userId = userIdentityAuth.getCurrentUserId();
        String refreshToken = request.getHeader("refreshToken");
        userService.loginOut(userId, refreshToken);

        return JsonResponseUtil.success();
    }

    //刷新accessToken
    @PostMapping("/refreshAccessToken")
    public JsonResponseUtil<String> refreshAccessToken(HttpServletRequest request){
        Long userId = userIdentityAuth.getCurrentUserId();
        String refreshToken = request.getHeader("refreshToken");
        String accessToken = userService.refreshAccessToken(userId, refreshToken);

        return JsonResponseUtil.success(accessToken);
    }

    //编辑
    @PostMapping("/editUser")
    public JsonResponseUtil<String> editUser(User user){
        Long userId = userIdentityAuth.getCurrentUserId();
        user.setId(userId);
        userService.editUser(user);

        return JsonResponseUtil.success();
    }

    //获取RSA公钥
    @GetMapping("/getUserRsaPublicKey")
    public JsonResponseUtil<String> getUserRsaPublicKey(){
        Long userId = userIdentityAuth.getCurrentUserId();
        String publicKeyBase64 = userService.getUserRsaKeyByUserId(userId).get("publicKeyBase64");

        return JsonResponseUtil.success(publicKeyBase64);
    }

    //获取用户列表
    @GetMapping("getUserList")
    public JsonResponseUtil<PageResultUtil<User>> getUserList(@RequestParam(defaultValue = "1") Integer page,
                                                              @RequestParam(defaultValue = "10") Integer size,
                                                              User user){
        PageResultUtil<User> userList = userService.getUserList(page, size, user);

        return JsonResponseUtil.success(userList);
    }

    //获取用户详情
    @GetMapping("/getUserDetail")
    public JsonResponseUtil<User> getUserDetail(){
        Long userId = userIdentityAuth.getCurrentUserId();
        User userDetail = userService.getUserDetail(userId);

        return JsonResponseUtil.success(userDetail);
    }

    //获取用户数量
    @GetMapping("/getUserTotal")
    public JsonResponseUtil<Integer> getUserTotal(User user){
        Integer total = userService.getUserTotal(user);

        return JsonResponseUtil.success(total);
    }

    //获取用户名称
    @GetMapping("/getUserName")
    public JsonResponseUtil<String> getUserName(){
        Long userId = userIdentityAuth.getCurrentUserId();
        String userName = userService.getUserDetail(userId).getName();

        return JsonResponseUtil.success(userName);
    }

    //获取用户访问记录列表
    @GetMapping("/getUserVisitRecordList")
    public JsonResponseUtil<PageResultUtil<UserVisitRecordDto>> getUserVisitRecordList(@RequestParam(defaultValue = "1") Integer startPage,
                                                                                       @RequestParam(defaultValue = "10") Integer size,
                                                                                       Long startTime,
                                                                                       Long endTime){
        PageResultUtil<UserVisitRecordDto> userVisitRecordList = userService.getUserVisitRecordList(startPage, size, startTime, endTime);

        return JsonResponseUtil.success(userVisitRecordList);
    }

    //获取用户访问记录数量
    @GetMapping("/getUserVisitRecordTotal")
    public JsonResponseUtil<Integer> getUserVisitRecordTotal(Long startTime,
                                                             Long endTime){
        Integer total = userService.getUserVisitRecordTotal(startTime, endTime);

        return JsonResponseUtil.success(total);
    }
}
