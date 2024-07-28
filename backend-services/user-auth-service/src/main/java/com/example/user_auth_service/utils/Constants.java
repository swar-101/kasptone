package com.example.user_auth_service.utils;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

     public static final String NXT_WELCOME_EMAIL_ID = "welcome@nxtlvl.com";
     public static final String NXT_WELCOME_SUBJECT = "Welcome to Nxtlvl";
     public static final String NXT_WELCOME_MSG_BODY = "Welcome to NxtlvlThreadz, You are registered successfully! \n " +
                    "For queries you can email to queries@nxtlvl.com \n" +
                    "To continue shopping you can go to www.nxtlvl.com";
     public static final String KAFKA_TOPIC_SIGNUP = "signup";

     public static final String EMAIL = "email";
     public static final String ISSUED_AT = "iat";
     public static final String ROLES = "roles";
     public static final String EXPIRES_AT = "exp";
}
