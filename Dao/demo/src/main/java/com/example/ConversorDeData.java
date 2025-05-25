package com.example;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversorDeData {
    private static final SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public static Date deStringParaDate(String data) throws Exception {
        return formato.parse(data);
    }

    public static String deDateParaString(Date data) {
        return formato.format(data);
    }
}