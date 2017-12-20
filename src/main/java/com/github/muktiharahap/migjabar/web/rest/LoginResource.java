package com.github.muktiharahap.migjabar.web.rest;

import com.github.muktiharahap.migjabar.repository.UserRepository;
import com.github.muktiharahap.migjabar.domain.User;
import com.github.muktiharahap.migjabar.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

/**
 * @author mukti on 10/4/2017.
 */
@RestController
@RequestMapping("/api")
public class LoginResource {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody @Valid UserDTO login){
        User user = userRepository.findOneByNikAndPassword(login.getUsername(),login.getPassword());

        Map<String, Object> hasil = new HashMap<>();

        if(user != null){
            hasil.put("success", true);
        } else {
            hasil.put("success", false);
        }

        return hasil;
    }
}
