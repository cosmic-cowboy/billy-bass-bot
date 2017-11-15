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
package com.linecorp.talking.bot.infra.line.api.receive;

import org.springframework.stereotype.Component;

import com.linecorp.talking.bot.infra.line.api.receive.response.Result;

import rx.Observable;
import rx.subjects.PublishSubject;

@Component
public class ReceiveEvent {

    /**
     * http://reactivex.io/RxJava/javadoc/rx/subjects/PublishSubject.html
     */
    final PublishSubject<Result> received = PublishSubject.create();

    /**
     * The Event to receive new Message or Operation from LINE Platform
     */
    public Observable<Result> received() {
        return received;
    }

}
