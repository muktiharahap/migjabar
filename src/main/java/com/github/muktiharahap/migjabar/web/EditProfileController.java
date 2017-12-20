package com.github.muktiharahap.migjabar.web;

import com.github.muktiharahap.migjabar.domain.User;
import com.github.muktiharahap.migjabar.repository.UserRepository;
import com.github.muktiharahap.migjabar.security.AuthoritiesConstants;
import com.github.muktiharahap.migjabar.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;

/**
 * @author mukti on 10/12/2017.
 */
@Controller
@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER})
public class EditProfileController {

    private final Logger log = LoggerFactory.getLogger(EditProfileController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/editprofile", method = RequestMethod.GET)
    public String showProfile(@RequestParam(value = "id", required = false) Long id, Model model) {
        User user = userRepository.findOneByNik(SecurityUtils.getCurrentUserLogin());
        log.info("user ditemukan :" +user.getId());
        if (user.getId() != null) {
            model.addAttribute("profile", user);
        }
        return "edit_profile";
    }

    @RequestMapping(value = "/editprofile", method = RequestMethod.POST)
    public String editProfile(@Valid User user, BindingResult errors, ModelMap mm, HttpSession session) {

        String lokasiPath = "/uploads";
        String lokasiTomcat = session.getServletContext().getRealPath(lokasiPath);
        log.info("Lokasi Tomcat dijalankan : ", lokasiTomcat);
        String lokasiTujuan = lokasiTomcat + File.separator;
        File folderTujuan = new File(lokasiTujuan);
        log.info("Lokasi files : ", folderTujuan);

        if (errors.hasErrors()) {
            log.info("WEB request to save User : {}", errors);
            mm.addAttribute("profile", user);
            return "edit_profile";
        }
//        User userUpdated = userRepository.findOne(user.getId());
//        userUpdated.setLogin(user.getLogin());
//        userUpdated.setFirstName(user.getFirstName());
//        userUpdated.setLastName(user.getLastName());
//        userUpdated.setEmail(user.getEmail());
//        userUpdated.setNik(user.getNik());
//        userUpdated.setNotel(user.getNotel());
        userRepository.save(user);

        return "redirect:/";
    }
}
