package com.yuelimin.sms.generator;

import java.util.Random;
import java.util.UUID;

/**
 * 签名生成
 *
 * @author yuelimin
 */
public class VerificationGeneratorUtil {
    /**
     * 生成验证码签名前缀
     *
     * @param prefix 前缀标识
     * @return 签名前缀 -> sms:tencent:aaa
     */
    public static String verificationStringPrefix(String prefix) {
        return prefix + UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成验证码签名编码
     *
     * @param length 生成签名编码长度
     * @return 十位随机整数字符串 -> 12345678890
     */
    public static String verificationStringCode(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            val.append(random.nextInt(10));
        }

        return val.toString();
    }
}
