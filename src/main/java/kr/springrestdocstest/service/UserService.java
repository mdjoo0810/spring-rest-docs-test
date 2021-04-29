package kr.springrestdocstest.service;

import kr.springrestdocstest.model.User;
import kr.springrestdocstest.model.UserCommand;
import kr.springrestdocstest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User create(UserCommand user) {
        User createUser = new User();
        createUser.setEmail(user.getEmail());
        createUser.setAccount(user.getAccount());
        createUser.setPhoneNumber(user.getPhoneNumber());
        createUser.setCreatedAt(LocalDateTime.now());
        return userRepository.save(createUser);
    }

    public User read(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    public User update(User requestUser) {
        Optional<User> optionalUser = userRepository.findById(requestUser.getId());

        return optionalUser.map(dbUser -> {
            dbUser.setAccount(requestUser.getAccount());
            dbUser.setEmail(requestUser.getEmail());
            dbUser.setPhoneNumber(requestUser.getPhoneNumber());
            dbUser.setUpdatedAt(LocalDateTime.now());

            return dbUser;
        })
                .map(userRepository::save)
                .orElse(null);
    }

    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }

}
