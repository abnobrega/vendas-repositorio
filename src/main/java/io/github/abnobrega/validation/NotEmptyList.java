package io.github.abnobrega.validation;

import io.github.abnobrega.validation.constraintValidation.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Retenção verificada em tempo e execução
@Target(ElementType.FIELD)          // O alvo dessa annotation (Retenção) é nos campos (Fields)
@Constraint(validatedBy = NotEmptyListValidator.class) // Define que essa annotation (retenção) é de validação
public @interface NotEmptyList {
    String message() default "A lista não pode ser vazia.";  // Lança uma mensagem genérica.
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
