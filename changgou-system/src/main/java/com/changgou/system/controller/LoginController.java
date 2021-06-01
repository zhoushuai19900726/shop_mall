package com.changgou.system.controller;

import com.changgou.system.pojo.Admin;
import com.changgou.system.service.AdminService;
import com.changgou.system.utils.Digests;
import com.changgou.system.utils.RandCodeImageUtils;
import entity.Result;
import entity.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.search.controller *
 * @since 1.0
 */
@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private AdminService adminService;

    /**
     * 跳转进入登陆页面
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登陆验证  TODO 集成轻量级shiro框架
     * @param request
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/checkLogin")
    @ResponseBody
    public Result<Admin> checkLogin(
            HttpServletRequest request,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("captcha") String captcha) {
        // 用户名非空验证
        if(StringUtils.isBlank(username)){
            return new Result<>(false, StatusCode.LOGINERROR, "用户名不可为空");
        }
        // 密码非空验证
        if(StringUtils.isBlank(password)){
            return new Result<>(false, StatusCode.LOGINERROR, "密码不可为空");
        }
        // 验证码非空验证
        if(StringUtils.isBlank(captcha)){
            return new Result<>(false, StatusCode.LOGINERROR, "验证码不可为空");
        }
        // 验证码验证
        if(!StringUtils.equals(String.valueOf(request.getSession().getAttribute(RandCodeImageUtils.SESSION_KEY_OF_RAND_CODE)).toUpperCase(), captcha.toUpperCase())){
            return new Result<>(false, StatusCode.LOGINERROR, "验证码错误");
        }
        // 查询管理员用户
        Admin queryAdmin = adminService.findByLoginName(username);
        if(Objects.isNull(queryAdmin)){
            return new Result<>(false, StatusCode.LOGINERROR, "用户名错误");
        }
        //  密码验证
        if(!Digests.validatePassword(password, queryAdmin.getPassword())){
            return new Result<>(false, StatusCode.LOGINERROR, "密码错误");
        }
        return new Result<>(true, StatusCode.OK, "验证成功", queryAdmin);
    }

    /**
     * 生成验证码图片io流
     * @param response
     * @param request
     */
    @GetMapping(value = "/generateImage")
    public void generateImage(HttpServletResponse response, HttpServletRequest request) throws IOException {
        RandCodeImageUtils.generateImage(response, request);
    }


    @PostMapping("/ADMIN_VERIFICATION_CODE")
    @ResponseBody
    public String ADMIN_VERIFICATION_CODE(HttpServletRequest request) {
        return String.valueOf(request.getSession().getAttribute(RandCodeImageUtils.SESSION_KEY_OF_RAND_CODE));
    }

}
