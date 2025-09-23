package com.lblog.api;

import com.lblog.common.util.JsonResponseUtil;
import com.lblog.domain.User;
import com.lblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserApi {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public JsonResponseUtil<Map<String, Object>> login(User user){
        Map<String, Object> tokenMap = userService.login(user);

        return JsonResponseUtil.success(tokenMap);
    }

    @PostMapping("/register")
    public JsonResponseUtil<Map<String, Object>> register(User user) {
        Map<String, Object> tokenMap = userService.register(user);

        return JsonResponseUtil.success(tokenMap);
    }

    @PostMapping("/loginOut")
    public JsonResponseUtil<String> loginOut(Long userId, String refreshToken){
        userService.loginOut(userId, refreshToken);

        return JsonResponseUtil.success();
    }

    @PostMapping("/refreshAccessToken")
    public JsonResponseUtil<String> refreshAccessToken(Long userId, String refreshToken){
        String accessToken = userService.refreshAccessToken(userId, refreshToken);

        return JsonResponseUtil.success(accessToken);
    }

    @PostMapping("/editUser")
    public JsonResponseUtil<String> editUser(User user){
        userService.editUser(user);

        return JsonResponseUtil.success();
    }

    @GetMapping("/rsaPublicKey")
    public JsonResponseUtil<String> getRsaPublicKey(Long userId){
        String publicKeyBase64 = userService.getUserRsaPublicKey(userId);

        return JsonResponseUtil.success(publicKeyBase64);
    }

    @GetMapping("/getUserDetail")
    public JsonResponseUtil<User> getUserDetail(Long userId){
        User userDetail = userService.getUserDetail(userId);

        return JsonResponseUtil.success(userDetail);
    }

    @GetMapping("getUserList")
    public JsonResponseUtil<User> getUserList(){
        User userList = userService.getUserList();

        return JsonResponseUtil.success(userList);
    }

    @PostMapping("/test")
    public Long test() {
        Long userId = userService.test();

        return userId;
    }
}
