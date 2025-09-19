package com.lblog.api;

import com.lblog.common.util.JsonResponseUtil;
import com.lblog.common.util.RSAUtil;
import com.lblog.domain.User;
import com.lblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;
import java.util.Map;

@RestController
public class UserApi {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public JsonResponseUtil<String> login(User user){
        String privateKey = userService.login(user);

        return JsonResponseUtil.success(privateKey);
    }

    @PostMapping("/register")
    public JsonResponseUtil<String> register(User user) {
        String privateKey = userService.register(user);

        return JsonResponseUtil.success(privateKey);
    }

    @DeleteMapping("/loginOut")
    public JsonResponseUtil<String> loginOut(Long userId){
        userService.loginOut(userId);

        return JsonResponseUtil.success();
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
    public String test() {
        PrivateKey testRes = userService.test();
        StringBuilder result = new StringBuilder();
        result.append("=== 私钥信息 ===\n");
        result.append(testRes);

        return result.toString();
    }
}
