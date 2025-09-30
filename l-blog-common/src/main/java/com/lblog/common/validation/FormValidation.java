package com.lblog.common.validation;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidation {
    private static final Pattern PHONE_REGEX = Pattern.compile("^1\\d{10}$");
    private static final Integer MIN_USERNAME_LENGTH = 1;
    private static final Integer MAX_USERNAME_LENGTH = 20;
    private static final Integer MIN_PASSWORD_LENGTH = 1;
    private static final Integer MAX_PASSWORD_LENGTH = 20;

    //校验手机号格式
    public static boolean phoneValidation(String phone){
        Matcher matcher = PHONE_REGEX.matcher(phone);

        return matcher.matches();
    }

    //校验用户名格式
    public static boolean userNameValidation(String userName){
        if(StringUtils.isBlank(userName)){
            return false;
        }
        Integer userNameLength = userName.length();
        if((userNameLength < MIN_USERNAME_LENGTH) || (userNameLength > MAX_USERNAME_LENGTH)){
            return false;
        }

        return true;
    }

    //校验密码格式
    public static boolean passwordValidation(String password){
        if(StringUtils.isBlank(password)){
            return false;
        }
        Integer passwordLength = password.length();
        if((passwordLength < MIN_PASSWORD_LENGTH) || (passwordLength > MAX_PASSWORD_LENGTH)){
            return false;
        }

        return true;
    }
}
