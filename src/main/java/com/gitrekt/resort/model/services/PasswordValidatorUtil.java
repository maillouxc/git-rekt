package com.gitrekt.resort.model.services;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordValidator;

public class PasswordValidatorUtil {
    
    public static final PasswordValidator validator = new PasswordValidator(
        // 60 is around the max length of BCrypt passwords anyways
        new LengthRule(10,60),
        new CharacterRule(EnglishCharacterData.UpperCase, 1),
        new CharacterRule(EnglishCharacterData.LowerCase, 1),
        new CharacterRule(EnglishCharacterData.Digit, 1),
        new CharacterRule(EnglishCharacterData.Special, 1)
    );
    
}
