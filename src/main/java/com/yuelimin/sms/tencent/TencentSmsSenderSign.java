package com.yuelimin.sms.tencent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.qcloudsms.SmsSenderUtil;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.github.qcloudsms.httpclient.HTTPMethod;
import com.github.qcloudsms.httpclient.HTTPRequest;
import com.github.qcloudsms.httpclient.HTTPResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuelimin
 */
@Slf4j
public class TencentSmsSenderSign extends SmsSingleSender {
    @Value("${sms.qcloud.url}")
    private String url;

    TencentSmsSenderSign(int appId, String appKey) {
        super(appId, appKey);
    }

    @Override
    public SmsSingleSenderResult sendWithParam(String nationCode,
                                               String phoneNumber,
                                               int templateId,
                                               String[] params,
                                               String sign,
                                               String extend,
                                               String ext) throws IOException {
        return this.sendWithParam(nationCode, phoneNumber, templateId, new ArrayList<>(Arrays.asList(params)), sign, extend, ext);
    }

    @Override
    public SmsSingleSenderResult sendWithParam(String nationCode,
                                               String phoneNumber,
                                               int templateId,
                                               ArrayList<String> params,
                                               String sign,
                                               String extend,
                                               String ext) throws IOException {
        long random = SmsSenderUtil.getRandom();
        long now = SmsSenderUtil.getCurrentTime();

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> body = new HashMap<>();
        Map<String, String> telMap = new HashMap<>();

        telMap.put("mobile", phoneNumber);
        telMap.put("nationcode", nationCode);

        body.put("tel", telMap);
        body.put("params", params);
        body.put("tpl_id", templateId);
        body.put("sig", SmsSenderUtil.calculateSignature(this.appkey, random, now, phoneNumber));
        body.put("sign", sign);
        body.put("time", now);
        body.put("extend", SmsSenderUtil.isNotEmpty(extend) ? extend : "");
        body.put("ext", SmsSenderUtil.isNotEmpty(ext) ? ext : "");

        log.info("#sendWithParam() -> param {}", body);

        HTTPRequest req = (new HTTPRequest(HTTPMethod.POST, this.url)).addHeader("Conetent-Type", "application/json")
                .addQueryParameter("sdkappid", this.appid)
                .addQueryParameter("random", random)
                .setConnectionTimeout('\uea60')
                .setRequestTimeout('\uea60')
                .setBody(objectMapper.writeValueAsString(body));

        try {
            HTTPResponse e = this.httpclient.fetch(req);
            this.handleError(e);
            return (new SmsSingleSenderResult()).parseFromHTTPResponse(e);
        } catch (URISyntaxException | IOException | HTTPException var15) {
            throw new RuntimeException("API url has been modified, current url: " + this.url);
        }
    }
}
