package com.yuelimin.controller;

import com.yuelimin.common.rest.RestBaseResponse;
import com.yuelimin.sms.tencent.TencentSmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author yuelimin
 */
@RestController
@RequestMapping("/sms/tencent")
@Tag(name = "TencentSmsController", description = "腾讯短信服务web接口")
public class TencentSmsController {
    @Resource
    private TencentSmsService tencentSmsService;

    @Operation(summary = "生成验证码信息")
    @PostMapping(value = "/generate/{effectiveTime}")
    public RestBaseResponse<?> generateVerificationInfo(@PathVariable("effectiveTime") int effectiveTime, @RequestBody Map<String, Object> payload) {
        return RestBaseResponse.success(tencentSmsService.generateVerificationInfo(payload, effectiveTime));
    }

    @Operation(summary = "校验手机验证码")
    @GetMapping("/verification/{key}/{code}")
    public RestBaseResponse<?> verification(@PathVariable("key") String key, @PathVariable("code") String code) {
        return RestBaseResponse.success(tencentSmsService.verify(key, code));
    }

}
