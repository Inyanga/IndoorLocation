package com.test.indoorlocation.ble;

public class ByteParser {
    public static int shortToInt (byte b1, byte b2) {
        return ((b1 & 0xff) << 8) | (b2 & 0xff);
    }
}
