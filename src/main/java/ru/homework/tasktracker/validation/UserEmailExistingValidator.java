package ru.homework.tasktracker.validation;

import lombok.RequiredArgsConstructor;
import ru.homework.tasktracker.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UserEmailExistingValidator implements ConstraintValidator<EmailIsNotExistInDataBase, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.findByEmail(value).isEmpty();
    }
}
