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
package com.linecorp.talking.bot.infra.util;

import org.apache.log4j.Logger;

import rx.Observable;
import rx.functions.Action1;

public class SubscribeUtils {

    private SubscribeUtils() { };

    private static final Logger logger = Logger.getLogger(SubscribeUtils.class);

    public static <T> void subscribe(Observable<T> observable, final Action1<? super T> onNext) {
        observable.doOnNext(onNext).doOnError(error -> errorHandling(error)).retry().subscribe();
    }

    private static void errorHandling(Throwable error) {
        logger.error(error);
    }
}
