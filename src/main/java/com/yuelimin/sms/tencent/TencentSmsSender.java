package com.yuelimin.sms.tencent;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.yuelimin.common.code.error.EnumCommonErrorCode;
import com.yuelimin.common.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author yuelimin
 */
@Slf4j
@Service
public class TencentSmsSender {
    @Value("${sms.qcloud.appId}")
    private Integer appId;
    @Value("${sms.qcloud.appKey}")
    private String appKey;
    @Value("${sms.qcloud.templateId}")
    private int templateId;
    @Value("${sms.qcloud.sign}")
    private String sign;

    public void send(String mobile, String code, int effectiveTime) throws BusinessException {
        log.info("给手机号{}发送验证码{}, 过期时间为{}秒", mobile, code, effectiveTime);

        SmsSingleSenderResult senderResult;
        try {
            TencentSmsSenderSign tencentSmsSenderSign = new TencentSmsSenderSign(appId, appKey);
            senderResult = tencentSmsSenderSign.sendWithParam("86",
                    mobile,
                    templateId,
                    new String[]{code, String.valueOf(effectiveTime / 60)},
                    sign,
                    "",
                    "");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("短信发送执行失败");
        }
        if (senderResult.result != EnumCommonErrorCode.SUCCESS.getCode()) {
            log.warn("QCloud return: {}", senderResult.toString());
            throw new BusinessException("云短信服务请求失败");
        }
    }

    public void sendOnConsole(String mobile, String code, int effectiveTime) {
        log.info("给手机号{}发送验证码{}, 过期时间为{}秒", mobile, code, effectiveTime);
    }
}
