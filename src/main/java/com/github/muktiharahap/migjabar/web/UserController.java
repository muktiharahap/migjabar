package com.github.muktiharahap.migjabar.web;

import com.github.muktiharahap.migjabar.config.AuthoritiesConstants;
import com.github.muktiharahap.migjabar.domain.Role;
import com.github.muktiharahap.migjabar.repository.MitraRepository;
import com.github.muktiharahap.migjabar.repository.RoleRepository;
import com.github.muktiharahap.migjabar.repository.UserRepository;
import com.github.muktiharahap.migjabar.security.SecurityUtils;
import com.github.muktiharahap.migjabar.service.MailService;
import com.github.muktiharahap.migjabar.service.util.RandomUtil;
import com.github.muktiharahap.migjabar.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * @author mukti on 9/30/2017.
 */
@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MitraRepository mitraDao;

    @Autowired
    private MailService mailService;

    @RequestMapping("/user/list")
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER})
    public String list(Model m, @PageableDefault(size = 10) Pageable pageable,
                            @RequestParam(name = "value", required = false) String value) throws Exception {
        User user = userRepository.findOneByNik(SecurityUtils.getCurrentUserLogin());
        if (user.getId() != null && !user.getLogin().equals("admin")) {
            m.addAttribute("data", userRepository.findOneByNik(user.getNik(), pageable));
        } else {
            if (value != null) {
                m.addAttribute("key", value);
                m.addAttribute("data", userRepository.findByLoginContainingIgnoreCase(value, pageable));
            } else {
                m.addAttribute("data", userRepository.findAll(pageable));
            }
        }

        return "user/list";
    }

    @GetMapping(value = "/user/form")
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER})
    public String show(@RequestParam(value = "id", required = false) Long id, Model model) {
        User user = new User();
        if (id != null) {
            user = userRepository.findOne(id);
        }

        model.addAttribute("user", user);
        model.addAttribute("listMitra", mitraDao.findAll());
        return "user/form";
    }

    @RequestMapping(value = "/user/form", method = RequestMethod.POST)
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER})
    public String create(@Valid User user, BindingResult errors, ModelMap mm) throws Exception{
        if (errors.hasErrors()) {
            log.info("WEB request to save User : {}", errors);
            mm.addAttribute("user", user);
            mm.addAttribute("listMitra", mitraDao.findAll());
            return "user/form";
        }

        Role role = roleRepository.findOneByRole(AuthoritiesConstants.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        String password = RandomUtil.generatePassword();
        user.setPassword(password);
        user.setRoles(roles);

        userRepository.save(user);

        // mailService.sendActivationEmail(user, password);

        return "redirect:/user/list";
    }

    @RequestMapping("/user/delete")
    @Secured({AuthoritiesConstants.ADMIN})
    public String delete(@RequestParam("id") Long id) {
        log.info("WEB request to delete User : {}", id);
        userRepository.delete(id);
        return "redirect:/user/list";
    }
}
