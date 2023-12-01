package org.example.management;

import java.nio.charset.StandardCharsets;

public class ByteToString {
    public static String byteArrayToString(byte[] byteArray, int startIndex, int endIndex) {
        // 변환할 범위의 길이 계산
        int length = endIndex - startIndex;

        // 새로운 byte 배열 생성하여 복사
        byte[] subArray = new byte[length];
        System.arraycopy(byteArray, startIndex, subArray, 0, length);

        // String으로 변환
        String resultString = new String(subArray, StandardCharsets.UTF_16LE);

        return resultString;
    }
}
