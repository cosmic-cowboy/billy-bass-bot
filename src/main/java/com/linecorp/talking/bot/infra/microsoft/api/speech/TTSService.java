/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api.speech;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.Set;
import java.util.TreeSet;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.linecorp.talking.bot.infra.fileio.StorageAccessService;
import com.linecorp.talking.bot.infra.microsoft.api.speech.request.AudioOutputFormat;
import com.linecorp.talking.bot.infra.microsoft.api.speech.request.VoiceFont;
import com.linecorp.talking.bot.infra.microsoft.api.speech.request.XmlDom;
import com.linecorp.talking.bot.infra.speech.SpeechQuery;
import com.linecorp.talking.bot.infra.speech.SpeechRepository;

@Component
public class TTSService {

    private static final Logger logger = Logger.getLogger(TTSService.class);

    private static String synthesizeUri = "https://speech.platform.bing.com/synthesize";

//    private static String speechUri = "https://speech.platform.bing.com/speech/recognition/conversation/cognitiveservices/v1?language=ja-JP";

    private static AudioOutputFormat outputFormat = AudioOutputFormat.Audio16Khz128BitTrateMonoMp3;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private StorageAccessService storageAccessService;

    @Autowired
    private SpeechRepository speechRepository;

    @Autowired
    private SpeechQuery speechQuery;

    @Autowired
    private SpeechService speechService;

    public String speechToText(File audioFile) throws ProtocolException, IOException, Exception{
        return getText(audioFile);
    }

    /**
     * Synthesize the voice through the specified parameters.
     */
    public void synthesize(final String textToSynthesize, final VoiceFont voiceFont, final String timestamp) throws Exception {
        InputStream is = getVoice(textToSynthesize, voiceFont);
        if (logger.isDebugEnabled()) {
            logger.debug("is is : " + is);
        }
        File file = new File("/tmp/" + timestamp);
        if (logger.isDebugEnabled()) {
            logger.debug("filename is : " + file.getName());
        }
        storageAccessService.write(is, file);
        speechRepository.create(timestamp, timestamp);
    }

    public Set<String> voices(){
        return new TreeSet<>(speechQuery.keys());
    }

    public void delete(final String filename){
        speechRepository.delete(filename);
    }

    public String getToken() {
        return authenticationService.getAccessToken();
    }

    private InputStream getVoice(final String textToSynthesize, final VoiceFont voiceFont)
            throws Exception, ProtocolException, IOException {
        final String accessToken = authenticationService.getAccessToken();

        HttpsURLConnection webRequest = HttpsConnection.getHttpsConnection(synthesizeUri);
        webRequest.setDoInput(true);
        webRequest.setDoOutput(true);
        webRequest.setConnectTimeout(5000);
        webRequest.setReadTimeout(15000);
        webRequest.setRequestMethod("POST");

        webRequest.setRequestProperty("Content-Type", "application/ssml+xml");
        webRequest.setRequestProperty("X-Microsoft-OutputFormat", outputFormat.value);
        webRequest.setRequestProperty("Authorization", "Bearer " + accessToken);
        webRequest.setRequestProperty("Accept", "*/*");

        String body = XmlDom.createDom(voiceFont, textToSynthesize);
        byte[] bytes = body.getBytes();
        webRequest.setRequestProperty("content-length", String.valueOf(bytes.length));
        webRequest.connect();

        DataOutputStream dop = new DataOutputStream(webRequest.getOutputStream());
        dop.write(bytes);
        dop.flush();
        dop.close();

        InputStream is = webRequest.getInputStream();
        return is;
    }

    private String getText(File audioFile) {
        final String accessToken = authenticationService.getAccessToken();
        speechService.speechToText(audioFile, accessToken);
        return "録音されたデータ";
    }

//    private String getText(InputStream is)
//            throws Exception, ProtocolException, IOException {
//        final String accessToken = authenticationService.getAccessToken();
//
//        HttpsURLConnection webRequest = HttpsConnection.getHttpsConnection(speechUri);
//        webRequest.setDoInput(true);
//        webRequest.setDoOutput(true);
//        webRequest.setConnectTimeout(5000);
//        webRequest.setReadTimeout(15000);
//        webRequest.setRequestMethod("POST");
//
//        webRequest.setRequestProperty("Content-Type", "audio/wav");
//        webRequest.setRequestProperty("codec", "audio/pcm");
//        webRequest.setRequestProperty("samplerate", "16000");
//        webRequest.setRequestProperty("Transfer-Encoding", "chunked");
//        webRequest.setRequestProperty("Authorization", "Bearer " + accessToken);
//
//        OutputStream outputStream = webRequest.getOutputStream();
//        byte[] buf = new byte[8192];
//        int c = 0;
//        while ((c = is.read(buf, 0, buf.length)) > 0) {
//            outputStream.write(buf, 0, c);
//            outputStream.flush();
//        }
//        outputStream.close();
//        is.close();
//
//        webRequest.connect();
//        String responseData = "";
//        InputStream stream = webRequest.getInputStream();
//        StringBuffer sb = new StringBuffer();
//        String line = "";
//        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//        try {
//            stream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        responseData = sb.toString();
//        if(webRequest.getResponseCode() != 200) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("responseData : " + responseData);
//            }
//        }
//        webRequest.disconnect();
//        return "録音されたデータ";
//    }

}
