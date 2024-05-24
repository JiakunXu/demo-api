package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Client;
import com.alipay.easysdk.kernel.util.Signer;

public class Signature {

    /**
     *
     * @param content 报文
     * @param sign
     * @return
     * @throws Exception
     */
    static Boolean rsaCheck(String content, String sign) throws Exception {
        Client client = Factory.Util.AES()._kernel;

        if (client.isCertMode()) {
            return Signer.verify("\"" + content + "\"", sign, client.extractAlipayPublicKey(""));
        } else {
            return Signer.verify("\"" + content + "\"", sign, client.getConfig("alipayPublicKey"));
        }
    }

}
