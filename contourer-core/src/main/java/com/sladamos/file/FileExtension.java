package com.sladamos.file;

public enum FileExtension {
    ASC,
    UNKNOWN;

    public String strValue() {
        if (this == UNKNOWN) {
            throw new IllegalArgumentException("Unknown type");
        }
        return this.name().toLowerCase();
    }
}
