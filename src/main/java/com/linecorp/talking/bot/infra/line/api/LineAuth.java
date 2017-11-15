/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.linecorp.talking.bot.infra.line.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.google.common.base.Throwables;

import com.linecorp.talking.bot.infra.line.api.exception.IllegalChannelAccessException;

@Component
final class LineAuth {

    private static final Logger logger = Logger.getLogger(LineAuth.class);


    String getLineWebLoginUrl(final String state, final String channelId, final String loginDomain, final String cpdomain) {

        final String redirectUrl;
        try {
            redirectUrl = URLEncoder.encode(getRedirectUrl(cpdomain), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        final StringBuilder buf = new StringBuilder();
        buf.append(loginDomain + "dialog/oauth/weblogin?response_type=code");
        buf.append("&client_id=" + channelId);
        buf.append("&redirect_uri=" + redirectUrl);
        buf.append("&state=" + state);

        return buf.toString();
    }

    String getRedirectUrl(final String cpdomain) {
        return cpdomain +"auth";
    }


    /**
     * <p>Signature Validation</p>
     *
     * https://developers.line.me/businessconnect/development-bot-server#signature_validation<br/>
     */
    void validateSignature(
            final String channelSecret,
            final String channelSignatureComputedByTheLinePlatform,
            final String httpRequestBody) {

        // compute a signature
        String signature;
        try {
            SecretKeySpec key = new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
            byte[] source = httpRequestBody.getBytes("UTF-8");
            signature = Base64.encodeBase64String(mac.doFinal(source));
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            throw Throwables.propagate(e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("channelSecret:" + channelSecret);
            logger.debug("httpRequestBody:" + httpRequestBody);
            logger.debug("channelSignatureComputedByTheLinePlatform:" + channelSignatureComputedByTheLinePlatform);
            logger.debug("signature:" + signature);
        }

        // Compare X-LINE-CHANNELSIGNATURE request header string and the signature
        if(!channelSignatureComputedByTheLinePlatform.equals(signature)){
            throw new IllegalChannelAccessException();
        }
    }

    String decodeEncryptedString(
            final String channelSecret,
            final String encryptedString) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(hexToBytes(channelSecret), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decoded = Base64.decodeBase64(encryptedString);
            String decrypted = new String(cipher.doFinal(decoded), "UTF-8");
            return decrypted;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            throw Throwables.propagate(e);
        }
    }

    private byte[] hexToBytes(String hex) {
        byte[] bytes;
        try {
            bytes = new byte[hex.getBytes("UTF-8").length / 2];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte)Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
            }
            return bytes;
        } catch (UnsupportedEncodingException e) {
            throw Throwables.propagate(e);
        }
    }

}
