package com.shophouse.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext ctx) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate("Phone number is required").addConstraintViolation();
            return false;
        }
        // Simple regex for validating phone numbers (e.g., 10 digits)
        if (!phoneNumber.matches("\\d{10}")) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate("Phone number must be 10 digits").addConstraintViolation();
            return false;
        }
        return true;
    }
}
