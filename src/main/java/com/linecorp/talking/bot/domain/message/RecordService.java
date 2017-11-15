/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.domain.message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.linecorp.talking.bot.domain.message.creator.MessageCreator;
import com.linecorp.talking.bot.infra.line.api.LineAPIService;
import com.linecorp.talking.bot.infra.line.api.receive.response.Profile;
import com.linecorp.talking.bot.infra.line.api.request.Push;
import com.linecorp.talking.bot.infra.line.api.request.message.Message;
import com.linecorp.talking.bot.infra.microsoft.api.speech.TTSService;

@Component
public class RecordService {

    private static final Logger logger = Logger.getLogger(RecordService.class);

    private static final Pattern p = Pattern.compile("^[a-z]");

    @Autowired
    private LineAPIService lineAPIService;

    @Autowired
    private MessageCreator messageCreator;

    @Autowired
    private TTSService ttsService;

    public void sendTextFromVoice(InputStream is) throws ProtocolException, IOException, Exception {
        // store audio file
        File audioFile = new File(System.currentTimeMillis() + ".wav");
        storeAudioFile(is, audioFile);
        if (logger.isDebugEnabled()) {
            logger.debug("audioFile : " + audioFile.getAbsolutePath());
        }

        // call speech to text api
        String voiceText = ttsService.speechToText(audioFile);

        sendMessageToGroup(voiceText);
    }


    public void sendMessageToGroup(String voiceText) {
        Matcher m = p.matcher(voiceText);
        Profile profile = lineAPIService.profile("Uc30d8a8b6be3500ccf8c633f2852313d");
        String text;
        if (m.find()) {
            text = "Here is a message from " + profile.displayName + System.lineSeparator() + voiceText;
        } else {
            text = profile.displayName + "さんからのメッセージです。" + System.lineSeparator() + voiceText;
        }
        if (m.find()) {

        }
        // send message
        Message message = messageCreator.createTextMessage(Optional.of(text));
        lineAPIService.push(new Push("C54c4142d45e186f88f1b7b92a1152060", Collections.singletonList(message)));
    }

    public String getToken() {
        // send message
        return ttsService.getToken();
    }



    /**
     * @param input
     * @param audioFile
     */
    private void storeAudioFile(InputStream input, File audioFile) {
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        for(Mixer.Info info:infos){
            System.out.println(info.getName());
            System.out.println(info.getDescription());
        }
        try {
            AudioFormat format = getAudioFormat();
            System.out.println("Start capturing...");
            AudioInputStream ais = new AudioInputStream(input, format, 1000);
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, audioFile);
            System.out.println("Finish capturing...");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }



    private AudioFormat getAudioFormat() {
        float sampleRate = 48000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,channels, signed, bigEndian);
        return format;
    }

}
