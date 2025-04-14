package ir.piana.boot.superapp.captcha;

import java.io.File;
import java.io.FileOutputStream;

public class CaptchaTest {

    //@Test
    void test() throws Exception {
        for (int i = 0; i < 10; i++) {
            SpecCaptcha specCaptcha = new SpecCaptcha();
            specCaptcha.setLen(4);
            specCaptcha.setFont(i, 32f);
            System.out.println(specCaptcha.text());
            specCaptcha.out(new FileOutputStream(new File("C:/superapp-captcha/png/captcha" + i + ".png")));
        }
    }

    //@Test
    void testGIf() throws Exception {
        for (int i = 0; i < 10; i++) {
            GifCaptcha gifCaptcha = new GifCaptcha();
            gifCaptcha.setLen(5);
            gifCaptcha.setFont(i, 32f);
            System.out.println(gifCaptcha.text());
            gifCaptcha.out(new FileOutputStream(new File("C:/superapp-captcha/git/aa" + i + ".gif")));
        }
    }

    //@Test
    void testArit() throws Exception {
        for (int i = 0; i < 10; i++) {
            ArithmeticCaptcha specCaptcha = new ArithmeticCaptcha();
            specCaptcha.setLen(3);
            specCaptcha.setFont(i, 28f);
            System.out.println(specCaptcha.getArithmeticString() + " " + specCaptcha.text());
            specCaptcha.out(new FileOutputStream(new File("C:/superapp-captcha/arithmetic-png/aa" + i + ".png")));
        }
    }

    //@Test
    void testBase64() throws Exception {
        GifCaptcha specCaptcha = new GifCaptcha();
        System.out.println(specCaptcha.toBase64(""));
    }

}
