package com.lblog.api;

import com.lblog.api.auth.AdminIdentityAuth;
import com.lblog.common.util.GetClientIpUtil;
import com.lblog.common.util.JsonResponseUtil;
import com.lblog.common.util.PageResultUtil;
import com.lblog.domain.Admin;
import com.lblog.domain.AdminGroup;
import com.lblog.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AdminApi {

    @Autowired
    private AdminIdentityAuth adminIdentityAuth;

    @Autowired
    private AdminService adminService;

    //登录
    @PostMapping("/adminLogin")
    public JsonResponseUtil<Map<String, Object>> adminLogin(Admin admin, HttpServletRequest request){
        String adminIp = GetClientIpUtil.getClientIp(request);
        Map<String, Object> tokenMap = adminService.login(admin, adminIp);

        return JsonResponseUtil.success(tokenMap);
    }

    //添加
    @PostMapping("/addAdmin")
    public JsonResponseUtil<String> addAdmin(Admin admin, HttpServletRequest request){
        adminService.addAdmin(admin);

        return JsonResponseUtil.success();
    }

    //退出登录
    @PostMapping("/adminLoginOut")
    public JsonResponseUtil<String> adminLoginOut(HttpServletRequest request){
        Long adminId = adminIdentityAuth.getCurrentAdminId();
        String refreshToken = request.getHeader("adminRefreshToken");
        adminService.loginOut(adminId, refreshToken);

        return JsonResponseUtil.success();
    }

    //刷新accessToken
    @PostMapping("/adminRefreshAccessToken")
    public JsonResponseUtil<String> adminRefreshAccessToken(HttpServletRequest request){
        Long adminId = adminIdentityAuth.getCurrentAdminId();
        String refreshToken = request.getHeader("adminRefreshToken");
        String accessToken = adminService.refreshAccessToken(adminId, refreshToken);

        return JsonResponseUtil.success(accessToken);
    }

    //编辑
    @PostMapping("/editAdmin")
    public JsonResponseUtil<String> editAdmin(Admin admin){
        Long adminId = adminIdentityAuth.getCurrentAdminId();
        admin.setId(adminId);
        adminService.editAdmin(admin);

        return JsonResponseUtil.success();
    }

    //删除管理员
    @DeleteMapping("/deleteAdmin")
    public JsonResponseUtil<String> deleteAdmin(Long adminId){
        adminService.deleteAdmin(adminId);

        return JsonResponseUtil.success();
    }

    //获取RSA公钥
    @GetMapping("/getAdminRsaPublicKey")
    public JsonResponseUtil<String> getRsaPublicKey(){
        Long adminId = adminIdentityAuth.getCurrentAdminId();
        String publicKeyBase64 = adminService.getAdminRsaKeyByAdminId(adminId).get("publicKeyBase64");

        return JsonResponseUtil.success(publicKeyBase64);
    }

    //获取管理员列表
    @GetMapping("getAdminList")
    public JsonResponseUtil<PageResultUtil<Admin>> getAdminList(@RequestParam(defaultValue = "1") Integer startPage,
                                                                @RequestParam(defaultValue = "10") Integer size,
                                                                Admin admin,
                                                                AdminGroup adminGroup){
        PageResultUtil<Admin> adminList = adminService.getAdminList(startPage, size, admin, adminGroup);

        return JsonResponseUtil.success(adminList);
    }

    //获取管理员详情
    @GetMapping("/getAdminDetail")
    public JsonResponseUtil<Admin> getAdminDetail(){
        Long adminId = 1L;
        Admin adminDetail = adminService.getAdminDetail(adminId);

        return JsonResponseUtil.success(adminDetail);
    }

    //获取管理员数量
    @GetMapping("/getAdminTotal")
    public JsonResponseUtil<Integer> getAdminTotal(Admin admin){
        Integer total = adminService.getAdminTotal(admin);

        return JsonResponseUtil.success(total);
    }
}
