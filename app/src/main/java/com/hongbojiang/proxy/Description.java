package com.hongbojiang.proxy;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum Description {
    DESCRIPTION, EMPTY;

    @JsonValue
    public String toValue() {
        switch (this) {
        case DESCRIPTION: return "\u7f51\u6613\u4e91\u97f3\u4e50\u5b98\u65b9\u8d26\u53f7";
        case EMPTY: return "";
        }
        return null;
    }

    @JsonCreator
    public static Description forValue(String value) throws IOException {
        if (value.equals("\u7f51\u6613\u4e91\u97f3\u4e50\u5b98\u65b9\u8d26\u53f7")) return DESCRIPTION;
        if (value.equals("")) return EMPTY;
        throw new IOException("Cannot deserialize Description");
    }
}
