package dev.aziz.services;

public class EmailUtils {

    public static String getEmailMessage(String name, String host, String activationCode) {
        return "Hello " + name + ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n" +
                getVerificationUrl(host, activationCode) + "\n\nThe support Team";
    }

    public static String getVerificationUrl(String host, String activationCode) {
        return host + "/verify?activationCode=" + activationCode;
    }
}
