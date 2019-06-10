package com.charles.lib.file;

import java.io.File;

public enum FileType implements EnumValue {
    PDF("pdf"),
    JPG("jpg"),
    PNG("png");

    private final String value;
    private final String dotValue;

    FileType(String value) {
        this.value = value;
        this.dotValue = "." + value;
    }

    @Override
    public String value() {
        return value;
    }

    public String dotValue() {
        return dotValue;
    }

    public String filename(String name) {
        return name + dotValue;
    }

    public String concatFilePath(String fileDir, String fileBasename) {
        if (fileDir.endsWith(File.separator)) {
            return fileDir + fileBasename + dotValue;
        }
        return fileDir + File.separator + fileBasename + dotValue;
    }
}