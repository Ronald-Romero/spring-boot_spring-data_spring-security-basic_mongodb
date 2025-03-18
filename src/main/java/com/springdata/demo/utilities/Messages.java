package com.springdata.demo.utilities;

import java.util.ResourceBundle;

public class Messages {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages");

    public enum MessageKey {
        NO_USER_DATA_EXISTS,
        USER_ID_NO_FOUND,
        USER_NO_FOUND,
        USER_UPDATED,
        USER_NO_ADDITIONAL_DATA,
        USER_DELETE,
        USER_EXISTS,
        EMAIL_EXISTS,
        ADDITIONAL_DATA_EXISTS,
        INCORRECT_PASSWORD,
        ERROR_CREATING_USER,
    }

    public static String getMessage(MessageKey key, String... args) {
        String message = bundle.getString(key.name());
        return args.length > 0 ? String.format(message, (Object[]) args) : message;
    }
}
