/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api.speech.request;

/// <summary>
/// Voice output formats.
/// </summary>
public enum AudioOutputFormat {

        Raw8Khz8BitMonoMULaw("raw-8khz-8bit-mono-mulaw"),
        Raw16Khz16BitMonoPcm("raw-16khz-16bit-mono-pcm"),
        Riff8Khz8BitMonoMULaw("riff-16khz-16bit-mono-pcm"),
        Riff16Khz16BitMonoPcm("riff-16khz-16bit-mono-pcm"),
        Audio16Khz128BitTrateMonoMp3("audio-16khz-128kbitrate-mono-mp3"),
        Audio16Khz64BitTrateMonoMp3("audio-16khz-64kbitrate-mono-mp3"),
        Audio16Khz32BitTrateMonoMp3("audio-16khz-32kbitrate-mono-mp3");

        public final String value;

        private AudioOutputFormat(String value) {
            this.value = value;
        }

}
