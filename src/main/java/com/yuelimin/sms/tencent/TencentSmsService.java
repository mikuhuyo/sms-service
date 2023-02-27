package com.yuelimin.sms.tencent;

import com.yuelimin.sms.generator.VerificationGeneratorUtil;
import com.yuelimin.sms.store.RedisVerificationStore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuelimin
 */
@Service
public class TencentSmsService {
    @Resource
    private RedisVerificationStore redisVerificationStore;

    private void confusion(Map<String, Object> payload, String key, String code) {
        String mobile = String.valueOf(payload.get("mobile"));

        TencentSmsSender tencentSmsSender = new TencentSmsSender();

        // 腾讯云发送短信
        // smsService.send(mobile, code, getEffectiveTime());

        // 在控制台输出验证码
        tencentSmsSender.sendOnConsole(mobile, code, 300);
    }

    /**
     * 生成验证信息
     */
    public Map<String, String> generateVerificationInfo(Map<String, Object> payload, int effectiveTime) {
        effectiveTime = effectiveTime > 0 ? effectiveTime : 300;
        String key = VerificationGeneratorUtil.verificationStringPrefix("tencent:sms:");
        String code = VerificationGeneratorUtil.verificationStringCode(6);
        confusion(payload, key, code);

        Map<String, String> map = new HashMap<>();

        redisVerificationStore.set(key, code, effectiveTime);
        map.put("key", key);

        return map;
    }

    /**
     * 验证信息
     */
    public boolean verify(String verificationKey, String verificationCode) {
        if (StringUtils.isBlank(verificationKey) || StringUtils.isBlank(verificationCode)) {
            return false;
        }

        String code = redisVerificationStore.get(verificationKey);
        if (code == null) {
            return false;
        }

        return code.equals(verificationCode);
    }
}
