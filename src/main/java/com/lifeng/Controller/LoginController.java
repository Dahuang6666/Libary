package com.lifeng.Controller;
import com.google.code.kaptcha.Producer;
import com.lifeng.Pojo.Login;
import com.lifeng.Pojo.Result;
import com.lifeng.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.UUID;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private Producer captchaProducer;
    private final Map<String, String> captchaStorage = new ConcurrentHashMap<>();
    //登录
    @PostMapping
    public Result login(@RequestBody Login login) {
       return loginService.login(login)?
               Result.success("True"):Result.success("False");
    }
    //用户注册
    @PostMapping("/register/user")
    public Result  UserRegister(@RequestBody Login login) {
        login.setPermissions(1);
       return Result.success(loginService.register(login));
    }
    //(超管)管理员注册
    @PostMapping("/register/admin")
    public Result  AdminRegister(@RequestBody Login login) {
        login.setPermissions(0);
        return Result.success(loginService.register(login));
    }
    // 生成验证码
    @GetMapping("/captcha")
    public Map<String, String> getCaptcha() throws Exception {
        // 生成验证码文本
        String text = captchaProducer.createText();
        System.out.println("验证码：" + text);
        // 生成验证码图片
        BufferedImage image = captchaProducer.createImage(text);
        // 将图片转换为 Base64 编码
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());
        // 生成唯一标识符 captchaId
        String captchaId = UUID.randomUUID().toString();
        // 存储验证码到临时变量
        captchaStorage.put(captchaId, text);

        // 返回 JSON 响应，包含 Base64 图片和验证码标识
        return Map.of(
                "captcha", base64Image,
                "captchaId", captchaId
        );
    }

    // 验证验证码
    @PostMapping("/verifyCaptcha")
    public Map<String, Object> verifyCaptcha(@RequestBody Map<String, String> request) {
        String captchaId = request.get("captchaId");
        String userInput = request.get("captchaInput");

        // 从临时变量中取出验证码
        String correctCaptcha = captchaStorage.get(captchaId);

        Map<String, Object> response = Map.of("success", false); // 默认失败

        if (correctCaptcha != null && correctCaptcha.equalsIgnoreCase(userInput)) {
            response = Map.of("success", true);
            // 验证成功后，移除验证码
            captchaStorage.remove(captchaId);
        } else {
            response = Map.of("success", false, "message", "验证码错误或已失效");
        }
        return response;
    }
    //获取id
    @GetMapping("/{username}")
    public Result selectIdByUsername(@PathVariable String username){
        return Result.success(loginService.selectIdByUsername(username));
    }
    //修改用户名密码
    @PostMapping("/update")
   public Result updatePassword(@RequestBody Login login) {
        try{
            loginService.update(login);
            return Result.success("True");
        }
        catch(Exception e){
           return Result.success("False");
        }

   }
}
