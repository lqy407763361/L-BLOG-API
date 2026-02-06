package com.lblog.api;

import com.lblog.api.auth.UserIdentityAuth;
import com.lblog.common.util.*;
import com.lblog.domain.User;
import com.lblog.dto.UserDto;
import com.lblog.dto.UserVisitRecordDto;
import com.lblog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;
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
        String refreshToken = request.getHeader("refreshToken");
        PublicKey publicKey = RSAUtil.getPublicKey();
        Long userId = JwtTokenUtil.validateToken(refreshToken, publicKey);
        String accessToken = userService.refreshAccessToken(userId, refreshToken);

        return JsonResponseUtil.success(accessToken);
    }

    //编辑
    @PostMapping("/editUser")
    public JsonResponseUtil<String> editUser(@RequestBody User user){
        Long userId = userIdentityAuth.getCurrentUserId();
        user.setId(userId);
        userService.editUser(user);

        return JsonResponseUtil.success();
    }

    //编辑（管理员后台）
    @PostMapping("/editUserByAdmin")
    public JsonResponseUtil<String> editUserByAdmin(@RequestBody User user){
        userService.editUser(user);

        return JsonResponseUtil.success();
    }

    //删除用户
    @DeleteMapping("/deleteUser")
    public JsonResponseUtil<String> deleteUser(@RequestBody Map<String, List<Long>> userId){
        userService.deleteUser(userId);

        return JsonResponseUtil.success();
    }

    //获取RSA公钥
    @GetMapping("/getUserRsaPublicKey")
    public JsonResponseUtil<String> getUserRsaPublicKey(){
        String publicKeyBase64 = RSAUtil.getPublicKeyBase64();

        return JsonResponseUtil.success(publicKeyBase64);
    }

    //获取用户列表
    @GetMapping("getUserList")
    public JsonResponseUtil<PageResultUtil<User>> getUserList(@RequestParam(defaultValue = "1") Integer page,
                                                              User user,
                                                              String moudle){
        PageResultUtil<User> userList = userService.getUserList(page, user, moudle);

        return JsonResponseUtil.success(userList);
    }

    //获取用户详情
    @GetMapping("/getUserDetail")
    public JsonResponseUtil<UserDto> getUserDetailDto(){
        Long userId = userIdentityAuth.getCurrentUserId();
        UserDto userDetail = userService.getUserDetailDto(userId);

        return JsonResponseUtil.success(userDetail);
    }

    //获取用户详情（管理员后台用户列表和详情使用）
    @GetMapping("/getUserDetailByAdmin")
    public JsonResponseUtil<UserDto> getUserDetailDtoByAdmin(Long userId){
        UserDto userDetail = userService.getUserDetailDto(userId);

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
