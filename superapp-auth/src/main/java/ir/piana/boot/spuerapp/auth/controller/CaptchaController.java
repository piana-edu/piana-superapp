package ir.piana.boot.spuerapp.auth.controller;

import ir.piana.boot.spuerapp.auth.redis.OtpSessionEntry;
import ir.piana.boot.spuerapp.auth.redis.RedisEntry;
import ir.piana.boot.superapp.captcha.GifCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/v1/auth")
public class CaptchaController {
    private final RedisEntry<OtpSessionEntry> redisEntry;

    public CaptchaController(
            @Qualifier("redisOtpSessionEntry") RedisEntry<OtpSessionEntry> redisEntry) {
        this.redisEntry = redisEntry;
    }

    @GetMapping(value = "captcha", produces = MediaType.IMAGE_GIF_VALUE)
    public void captcha(
            @RequestParam("for") String forKey,
            @RequestParam("time") String time,
            HttpSession session,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        GifCaptcha gifCaptcha = new GifCaptcha();
        gifCaptcha.setLen(5);
        gifCaptcha.setFont(0, 32f);
        session.setAttribute("captcha-for-" + forKey, gifCaptcha.text());
        response.setContentType(MediaType.IMAGE_GIF_VALUE);
        gifCaptcha.out(response.getOutputStream());
    }
}
