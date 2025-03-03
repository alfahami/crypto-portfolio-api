package com.codelogium.portfolioservice.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.codelogium.portfolioservice.entity.User;
import com.codelogium.portfolioservice.exception.ResourceNotFoundException;
import com.codelogium.portfolioservice.respository.UserRepository;

import jakarta.transaction.Transactional;

import static com.codelogium.portfolioservice.util.EntityUtils.updateIfNotNull;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    @Override
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public User retrieveUser(Long id) {
        return unwrapUser(id, userRepository.findById(id));
    }

    @Transactional // commit changes or roll back completely if failure
    @Override
    public User updateUser(Long id, User newUser) {

        User existingUser = unwrapUser(id, userRepository.findById(id));
        //No need to ignore Id tampering as we're modifying the retrieved object for the repository
        // newUser.setId(id); // ignore ID request in the body as it might be intentionally changed

        // Only update fields if they're not null in newUser
        updateIfNotNull(existingUser::setFirstName, newUser.getFirstName());
        updateIfNotNull(existingUser::setLastName, newUser.getLastName());
        updateIfNotNull(existingUser::setBirthDate, newUser.getBirthDate());
        updateIfNotNull(existingUser::setProfession, newUser.getProfession());

        return userRepository.save(existingUser);
    }

    @Override
    public void removeUser(Long id) {
        User existingUser = unwrapUser(id, userRepository.findById(id));
        userRepository.delete(existingUser);
    }

    public static User unwrapUser(Long id, Optional<User> optionalUser) {
        if (optionalUser.isPresent())
            return optionalUser.get();
        else
            throw new ResourceNotFoundException(id, User.class);
    }
}
