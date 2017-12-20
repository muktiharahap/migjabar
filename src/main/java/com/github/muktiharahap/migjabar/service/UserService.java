package com.github.muktiharahap.migjabar.service;

import com.github.muktiharahap.migjabar.domain.User;
import com.github.muktiharahap.migjabar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mukti on 9/30/2017.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User simpan(String login, String password, String firstName, String lastName, String email, String imageUrl, Boolean activated, String nik, String notel, String emei) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setImageUrl(imageUrl);
        user.setActivated(activated);
        user.setNik(nik);
        user.setNotel(notel);
        user.setEmei(emei);
        userRepository.save(user);
        return user;
    }
}
