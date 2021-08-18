package com.example.imcommunity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotContainBlankValidator implements ConstraintValidator<NotContainBlank, String> {
    @Override
    public void initialize(NotContainBlank constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //null时不进行校验
        if (s != null && s.contains(" ")) {
            //获取默认提示信息
            String defaultConstraintMessageTemplate = constraintValidatorContext.getDefaultConstraintMessageTemplate();
            System.out.println("default message :" + defaultConstraintMessageTemplate);
            //禁用默认提示信息
            constraintValidatorContext.disableDefaultConstraintViolation();
            //设置提示语
            constraintValidatorContext.buildConstraintViolationWithTemplate("can not contains blank").addConstraintViolation();
            return false;
        }
        return true;
    }
}
