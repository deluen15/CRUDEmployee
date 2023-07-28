package com.example.employer.utils;

import lombok.Generated;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ResourceUtils {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);

    private ResourceUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String streamToString(InputStream inputStream) {
        try {

            String var2;
            try {
                var2 = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            } catch (Throwable var5) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable var4) {
                        var5.addSuppressed(var4);
                    }
                }

                throw var5;
            }

            inputStream.close();

            return var2;
        } catch (IOException var6) {
            log.error("error reading input stream", var6);
            return null;
        }
    }

    public static String loadFile(String fileName) {
        return streamToString(getFileFromResourceAsStream(fileName));
    }

    public static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = ResourceUtils.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}
