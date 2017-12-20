package com.github.muktiharahap.migjabar.web;

import com.github.muktiharahap.migjabar.repository.MitraRepository;
import com.github.muktiharahap.migjabar.domain.Mitra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author mukti on 9/30/2017.
 */
@Controller
public class MitraController {
    @Autowired
    private MitraRepository mitraRepository;

    private static final Logger log = LoggerFactory.getLogger(MitraController.class);

    @RequestMapping("/mitra/list")
    public String list(Model m, @PageableDefault(size = 10) Pageable pageable,
                             @RequestParam(name = "value", required = false) String value) throws Exception {

        if (value != null) {
            m.addAttribute("key", value);
            m.addAttribute("data", mitraRepository.findByNameContainingIgnoreCase(value, pageable));
        } else {
            m.addAttribute("data", mitraRepository.findAll(pageable));
        }
        return "mitra/list";
    }

    @GetMapping(value = "/mitra/form")
    public String show(@RequestParam(value = "id", required = false) Long id, Model model) {
        Mitra mitra = new Mitra();
        if (id != null) {
            mitra = mitraRepository.findOne(id);
        }

        model.addAttribute("mitra", mitra);
        return "mitra/form";
    }

    @RequestMapping(value = "/mitra/form", method = RequestMethod.POST)
    public String create(@Valid Mitra mitra, BindingResult errors, ModelMap mm) {
        if (errors.hasErrors()) {
            mm.addAttribute("mitra", mitra);
            return "mitra/form";
        }

        mitraRepository.save(mitra);

        return "redirect:/mitra/list";
    }

    @RequestMapping("/mitra/delete")
    public String delete(@RequestParam("id") Long id) {
        log.debug("WEB request to delete Mitra : {}", id);
        mitraRepository.delete(id);
        return "redirect:/mitra/list";
    }
}
