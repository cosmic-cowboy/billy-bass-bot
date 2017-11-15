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
package com.linecorp.talking.bot.infra.fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class StorageAccessService {

    @Autowired
    private Storage storage;

    public void write(final InputStream is, final File file) {
        storage.write(is, file);
    }

    public ResponseEntity<?> read(String filename) throws FileNotFoundException {
        InputStream in = storage.read(new File("/tmp/" + filename));
        HttpHeaders headers = new HttpHeaders();
        headers.setPragma("");
        return new ResponseEntity<Resource>(
                new InputStreamResource(in),
                headers,
                HttpStatus.OK);
    }

}
