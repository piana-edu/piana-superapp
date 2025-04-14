package ir.piana.boot.spuerapp.controller;

import ir.piana.boot.spuerapp.common.errors.APIResponseDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class EchoController {
    @GetMapping("echo")
    public ResponseEntity<APIResponseDto<String>> requestOtp(@RequestParam("val") String val, HttpSession session) {
        return ResponseEntity.ok(new APIResponseDto<>(false, val));
    }
}
