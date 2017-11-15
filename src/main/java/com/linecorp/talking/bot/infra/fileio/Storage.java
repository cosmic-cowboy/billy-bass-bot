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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.base.Throwables;

@Component
public class Storage {

    private static final Logger logger = Logger.getLogger(Storage.class);

    public void write(final InputStream is, final File file) {
        try {
            try {
                final Path path = file.toPath();
                if (file.exists()) {
                    Files.delete(path);
                }
                Files.copy(is, path);
                if (logger.isDebugEnabled()) {
                    logger.debug("image saved at " + path);
                }
            } finally {
                is.close();
            }
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public InputStream read(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

}
