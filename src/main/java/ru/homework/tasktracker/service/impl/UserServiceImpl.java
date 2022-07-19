package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.filter.UserFilter;
import ru.homework.tasktracker.repository.UserRepository;
import ru.homework.tasktracker.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparingInt;
import static org.springframework.data.jpa.domain.Specification.where;
import static ru.homework.tasktracker.specification.UserSpecification.generateSpecificationByUserFilter;
import static ru.homework.tasktracker.specification.UserSpecification.getUserById;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Пользователя с id %s не существует", id)));
    }

    @Override
    public User findById(Long id, UserFilter userFilter) {
        User user =  userRepository.findOne(where(getUserById(id))
                .and(generateSpecificationByUserFilter(userFilter)))
                .orElse(null);
        if (user == null) {
            user = findById(id);
            user.setTasks(new ArrayList<>());
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    @Override
    public User findUserWithMaxNumberTasks(UserFilter userFilter) {
        List<User> users = userRepository.findAll(generateSpecificationByUserFilter(userFilter));
        return users.stream()
                .max(comparingInt(u -> u.getTasks().size()))
                .orElseThrow(() -> new RuntimeException("С такими значениями фильтров никого не нашлось"));
    }

}
