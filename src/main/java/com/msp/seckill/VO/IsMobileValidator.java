package com.msp.seckill.VO;

import com.msp.seckill.utils.ValidatorUtil;
import com.msp.seckill.validator.IsMobile;
import org.apache.commons.lang3.StringUtils;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return ValidatorUtil.isMobile(s);
    }
}
