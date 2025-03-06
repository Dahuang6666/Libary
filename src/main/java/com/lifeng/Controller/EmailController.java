package com.lifeng.Controller;
import com.lifeng.Pojo.Result;
import com.lifeng.Pojo.VerifyRequest;
import com.lifeng.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // 临时存储验证码和用户名
    private final Map<String, String> verificationCodes = new HashMap<>();

    // 发送邮件
    @GetMapping("/send")
    public Result sendEmail(@RequestParam String username) {
        try {
            String code = emailService.sendSimpleEmail(username);
            // 将验证码和用户名存储到 Map 中
            verificationCodes.put(username, code);
            return Result.success("True");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success("False");
        }
    }

    // 验证验证码
    @PostMapping("/verify")
    public Result verifyCode(@RequestBody VerifyRequest request) {
        String username = request.getUsername();
        String code = request.getCode();

        // 从 Map 中获取存储的验证码
        String storedCode = verificationCodes.get(username);
        if (storedCode == null) {
            return Result.error("验证码错误或未发送");
        }

        // 比较验证码
        if (storedCode.equals(code)) {
            // 验证成功，删除存储的验证码
            verificationCodes.remove(username);
            return Result.success("True");
        } else {
            return Result.error("False");
        }
    }
}