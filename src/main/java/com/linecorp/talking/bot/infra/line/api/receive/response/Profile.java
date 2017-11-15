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
package com.linecorp.talking.bot.infra.line.api.receive.response;

import java.lang.reflect.Field;

/**
 * <p>the profile information of LINE</p>
 */
public final class Profile {

    public final String displayName;
    public final String userId;
    public final String pictureUrl;
    public final String statusMessage;

    public Profile(String displayName, String userId, String pictureUrl, String statusMessage) {
        this.displayName = displayName;
        this.userId = userId;
        this.pictureUrl = pictureUrl;
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Class: " + this.getClass().getCanonicalName() + "\n");
        sb.append("Settings:\n");
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                sb.append(field.getName() + " = " + field.get(this) + "\n");
            } catch (IllegalAccessException e) {
                sb.append(field.getName() + " = " + "access denied\n");
            }
        }
        return sb.toString();
    }

}
