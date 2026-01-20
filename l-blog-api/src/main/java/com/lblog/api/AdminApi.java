package com.lblog.api;

import com.lblog.api.auth.AdminIdentityAuth;
import com.lblog.common.util.GetClientIpUtil;
import com.lblog.common.util.JsonResponseUtil;
import com.lblog.common.util.PageResultUtil;
import com.lblog.common.util.RSAUtil;
import com.lblog.domain.Admin;
import com.lblog.domain.AdminGroup;
import com.lblog.dto.AdminDto;
import com.lblog.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminApi {

    @Autowired
    private AdminIdentityAuth adminIdentityAuth;

    @Autowired
    private AdminService adminService;

    //登录
    @PostMapping("/adminLogin")
    public JsonResponseUtil<Map<String, Object>> adminLogin(@RequestBody Admin admin, HttpServletRequest request, HttpSession session, String captchaCode){
        String adminIp = GetClientIpUtil.getClientIp(request);
        Map<String, Object> tokenMap = adminService.login(admin, adminIp, session, captchaCode);

        return JsonResponseUtil.success(tokenMap);
    }

    //添加
    @PostMapping("/addAdmin")
    public JsonResponseUtil<String> addAdmin(@RequestBody Admin admin){
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
    public JsonResponseUtil<String> editAdmin(@RequestBody Admin admin){
        Long adminId = adminIdentityAuth.getCurrentAdminId();
        admin.setId(adminId);
        adminService.editAdmin(admin);

        return JsonResponseUtil.success();
    }

    //编辑（管理员后台）
    @PostMapping("/editAdminByAdmin")
    public JsonResponseUtil<String> editAdminByAdmin(@RequestBody Admin admin){
        adminService.editAdmin(admin);

        return JsonResponseUtil.success();
    }

    //删除管理员
    @DeleteMapping("/deleteAdmin")
    public JsonResponseUtil<String> deleteAdmin(@RequestBody Map<String, List<Long>> adminId){
        adminService.deleteAdmin(adminId);

        return JsonResponseUtil.success();
    }

    //获取RSA公钥
    @GetMapping("/getAdminRsaPublicKey")
    public JsonResponseUtil<String> getRsaPublicKey(){
        String publicKeyBase64 = RSAUtil.getPublicKeyBase64();

        return JsonResponseUtil.success(publicKeyBase64);
    }

    //获取管理员列表
    @GetMapping("/getAdminList")
    public JsonResponseUtil<PageResultUtil<AdminDto>> getAdminList(@RequestParam(defaultValue = "1") Integer page,
                                                                   @RequestParam(defaultValue = "10") Integer size,
                                                                   Admin admin,
                                                                   AdminGroup adminGroup){
        PageResultUtil<AdminDto> adminList = adminService.getAdminList(page, size, admin, adminGroup);

        return JsonResponseUtil.success(adminList);
    }

    //获取管理员详情
    @GetMapping("/getAdminDetail")
    public JsonResponseUtil<AdminDto> getAdminDetailDto(){
        Long adminId = adminIdentityAuth.getCurrentAdminId();
        AdminDto adminDetail = adminService.getAdminDetailDto(adminId);

        return JsonResponseUtil.success(adminDetail);
    }

    //获取管理员详情（管理员后台）
    @GetMapping("/getAdminDetailByAdmin")
    public JsonResponseUtil<AdminDto> getAdminDetailDtoByAdmin(Long adminId){
        AdminDto adminDetail = adminService.getAdminDetailDto(adminId);

        return JsonResponseUtil.success(adminDetail);
    }

    //获取管理员数量
    @GetMapping("/getAdminTotal")
    public JsonResponseUtil<Integer> getAdminTotal(Admin admin){
        Integer total = adminService.getAdminTotal(admin);

        return JsonResponseUtil.success(total);
    }
}
