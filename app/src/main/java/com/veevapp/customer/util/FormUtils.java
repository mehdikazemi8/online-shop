package com.veevapp.customer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormUtils {
    public static boolean validEmail(String email) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean validCode(String code) {
        final Pattern VALID_CODE_REGEX =
                Pattern.compile("^[0-9]{4}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_CODE_REGEX.matcher(code);
        return matcher.find();
    }

    public static boolean validMobileNumber(String mobileNumber) {
        final Pattern VALID_MOBILE_NUMBER_REGEX =
                Pattern.compile("^09[0-9]{9}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_MOBILE_NUMBER_REGEX.matcher(mobileNumber);
        return matcher.find();
    }

    public static boolean validCustomerCardNumber(String mobileNumber) {
        final Pattern VALID_CUSTOMER_CARD_NUMBER_REGEX =
                Pattern.compile("^[0-9]{16,19}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_CUSTOMER_CARD_NUMBER_REGEX.matcher(mobileNumber);
        return matcher.find();
    }

    public static boolean validReferenceCode(String mobileNumber) {
        final Pattern VALID_REFERENCE_CODE_REGEX =
                Pattern.compile("^[0-9]{3,}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_REFERENCE_CODE_REGEX.matcher(mobileNumber);
        return matcher.find();
    }
}