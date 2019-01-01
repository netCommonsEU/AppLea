package com.example.commontask.utils;

/**
 * Created by Αρης on 22/9/2017.
 */

public class EmailEncoding {

    public static String commaEncodePeriod(String email) {
        return email != null ? email.replace(".", ",") : "";
    }

    public static String commaDecodePeriod(String email) {

        return email != null ? email.replace(",", ".") : "";
    }
}