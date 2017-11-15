/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api.speech.request;

import java.util.Arrays;
import java.util.Optional;

public enum VoiceFont {

    Catherine("catherine", "en-AU","Female","Microsoft Server Speech Text to Speech Voice (en-AU, Catherine)"),
    HayleyRUS("hayleyrus", "en-AU","Female","Microsoft Server Speech Text to Speech Voice (en-AU, HayleyRUS)"),
    Linda("linda", "en-CA","Female","Microsoft Server Speech Text to Speech Voice (en-CA, Linda)"),
    HeatherRUS("heatherrus", "en-CA","Female","Microsoft Server Speech Text to Speech Voice (en-CA, HeatherRUS)"),
    Susan("susan", "en-GB","Female","Microsoft Server Speech Text to Speech Voice (en-GB, Susan, Apollo)"),
    HazelRUS("hazelrus", "en-GB","Female","Microsoft Server Speech Text to Speech Voice (en-GB, HazelRUS)"),
    George("george", "en-GB","Male","Microsoft Server Speech Text to Speech Voice (en-GB, George, Apollo)"),
    Shaun("shaun", "en-IE","Male","Microsoft Server Speech Text to Speech Voice (en-IE, Shaun)"),
    Heera("heera", "en-IN","Female","Microsoft Server Speech Text to Speech Voice (en-IN, Heera, Apollo)"),
    PriyaRUS("priyarus", "en-IN","Female","Microsoft Server Speech Text to Speech Voice (en-IN, PriyaRUS)"),
    Ravi("ravi", "en-IN","Male","Microsoft Server Speech Text to Speech Voice (en-IN, Ravi, Apollo) "),
    ZiraRUS("zirarus", "en-US","Female","Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)"),
    JessaRUS("jessarus", "en-US","Female","Microsoft Server Speech Text to Speech Voice (en-US, JessaRUS)"),
    BenjaminRUS("benjaminrus", "en-US","Male","Microsoft Server Speech Text to Speech Voice (en-US, BenjaminRUS)"),
    Ayumi("ayumi", "ja-JP","Female","Microsoft Server Speech Text to Speech Voice (ja-JP, Ayumi, Apollo)"),
    Ichiro("ichiro", "ja-JP","Male","Microsoft Server Speech Text to Speech Voice (ja-JP, Ichiro, Apollo)");

    public final String name;
    public final String locale;
    public final String gender;
    public final String serviceName;

    private VoiceFont(String name, String locale, String gender, String serviceName) {
        this.name = name;
        this.locale = locale;
        this.gender = gender;
        this.serviceName = serviceName;
    }

    public static VoiceFont find(final String name) {
        final String lowerName = name.toLowerCase();
        final Optional<VoiceFont> optVoiceFont =
                Arrays.stream(values()).filter(e -> e.name.equals(lowerName)).findFirst();
        if (optVoiceFont.isPresent()){
            return optVoiceFont.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

}
