package com.example;

public class ValidadorEntrada {
    public static boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public static boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 6;
    }

    public static boolean validarTexto(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}