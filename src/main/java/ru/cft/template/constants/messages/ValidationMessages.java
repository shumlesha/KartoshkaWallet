package ru.cft.template.constants.messages;

import lombok.experimental.UtilityClass;

import java.util.ResourceBundle;

@UtilityClass
public class ValidationMessages {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages/validationmessage");

    // FIRSTNAME VALIDATION
    public static final String FIRSTNAME_NOTNULL_REQUIRED = "{firstname.notnull.required}";
    public static final String FIRSTNAME_NOTBLANK_REQUIRED = "{firstname.notblank.required}";
    public static final String FIRSTNAME_CONTAINS_ONLY_LETTERS = "{firstname.contains.only.letters}";
    public static final String FIRSTNAME_LENGTH_CONSTRAINT = "{firstname.length.constraint}";

    // LASTNAME VALIDATION
    public static final String LASTNAME_NOTNULL_REQUIRED = "{lastname.notnull.required}";
    public static final String LASTNAME_NOTBLANK_REQUIRED = "{lastname.notblank.required}";
    public static final String LASTNAME_CONTAINS_ONLY_LETTERS = "{lastname.contains.only.letters}";
    public static final String LASTNAME_LENGTH_CONSTRAINT = "{lastname.length.constraint}";

    // PATRONYMIC VALIDATION
    public static final String PATRONYMIC_NOT_CORRECT = "{patronymic.not.correct}";
    public static final String PATRONYMIC_LENGTH_CONSTRAINT = "{patronymic.length.constraint}";

    // PHONENUMBER VALIDATION
    public static final String PHONENUMBER_NOTCORRECT = "{phonenumber.not.correct}";

    // COMMENT VALIDATION
    public static final String COMMENT_LENGTH_CONSTRAINT = "{comment.length.constraint}";

    // EMAIL VALIDATION
    public static final String EMAIL_VALID_REQUIRED = "{email.valid.required}";

    // BIRTHDATE VALIDATION
    public static final String BIRTHDATE_PAST_REQUIRED = "{birthdate.past.required}";
    public static final String TOO_OLD_CONSTRAINT = "{too.old.constraint}";
    public static final String BIRTHDATE_NOT_NULL_REQUIRED = "{birthdate.not.null.required}";

    // PASSWORD VALIDATION
    public static final String PASSWORD_NOTNULL_REQUIRED = "{password.notnull.required}";
    public static final String PASSWORD_LENGTH_CONSTRAINT = "{password.length.constraint}";
    public static final String PASSWORD_RUSSIAN_CONSTRAINT = "{password.russian.constraint}";
    public static final String SPECIAL_SYMBOL_CONSTRAINT = "{special.symbol.constraint}";
    public static final String PASSWORD_NOT_CORRECT = "{password.not.correct}";

    // COST VALIDATION
    public static final String COST_NOTNULL_REQUIRED = "{cost.notnull.required}";
    public static final String COST_ONLY_POSITIVE_REQUIRED = "{cost.only.positive.required}";
    public static final String COST_DIGITS_CONSTRAINT = "{cost.digits.constraint}";


    // RECIPIENT VALIDATION
    public static final String RECIPIENT_NOTNULL_REQUIRED = "{recipient.notnull.required}";


    // PAYMENT DATA VALIDATION
    public static final String PAYMENT_DATA_INCORRECT = "{payment.data.incorrect}";
    public static final String ILLEGAL_PAYMENT_DATA = "{illegal.payment.data}";



    // COMMON VALIDATION
    public static final String VALIDATION_FAILED = bundle.getString("validation.failed");
    public static final String NOT_READABLE = bundle.getString("not.readable");
}
