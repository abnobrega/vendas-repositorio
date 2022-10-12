package io.github.abnobrega.validation.constraintValidation;

import io.github.abnobrega.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List> {

    //
    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        constraintAnnotation.message();
    }

    // Método que vai dizer se o objeto list é válido
    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        return (list != null && !list.isEmpty());
    }
}
