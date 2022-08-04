package ru.homework.tasktracker.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UserEmailExistingValidator.class)
public @interface EmailIsNotExistInDataBase {
    String message() default "Email not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
