package ru.cft.template.constants;

import lombok.experimental.UtilityClass;


@UtilityClass
public class RegexPatterns {
    public static final String FIRSTNAME_PATTERN = "[A-ZА-Я][a-zа-я]*";
    public static final String LASTNAME_PATTERN = "[A-ZА-Я][a-zа-я]*";
    public static final String PATRONYMIC_PATTERN = "[A-ZА-Я][a-zа-я]*";
    public static final String PHONE_NUMBER_PATTERN = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
    public static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final String RUSSIAN_LETTERS_PATTERN = "[А-Яа-я]";
    public static final String EXCLAMATION_PATTERN = "!";
    public static final String QUESTION_PATTERN = "\\?";
    public static final String COMMENT_PATTERN = ".{0,250}";
}
