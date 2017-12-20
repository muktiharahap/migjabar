package com.github.muktiharahap.migjabar.web;

import com.github.muktiharahap.migjabar.config.PDFBuilder;
import com.github.muktiharahap.migjabar.domain.Book;
import com.github.muktiharahap.migjabar.repository.JobRepository;
import com.github.muktiharahap.migjabar.domain.Job;
import com.github.muktiharahap.migjabar.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);

	@Autowired
    private JobRepository jobDao;

    @Autowired
    private ImageService imageService;

    @Value("${app.folder.upload}")
    public String uploadDir;

	@RequestMapping("/job/list")
    public String list(Model m, @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(name = "value", required = false) String value) throws Exception {

//        try {
//            Resource file = imageService.loadResource("MIG1507644208763.jpg", uploadDir);
//            log.info("File {}",file);
//        } catch (MalformedURLException ex) {
//            return null;
//        }

        if (value != null) {
            m.addAttribute("key", value);
            m.addAttribute("data", jobDao.findByNotelContainingIgnoreCase(value, pageable));
        } else {
            m.addAttribute("data", jobDao.findAll(pageable));
        }
        return "job/list";
    }

    @GetMapping(value = "/job/form")
    public String show(@RequestParam(value = "id", required = false) Long id, Model model) {
        Job job = new Job();
        if (id != null) {
            job = jobDao.findOne(id);
        }

        model.addAttribute("job", job);
        return "job/form";
    }

    @RequestMapping(value = "/job/form", method = RequestMethod.POST)
    public String create(@Valid Job job, BindingResult errors, ModelMap mm) {
        if (errors.hasErrors()) {
            mm.addAttribute("job", job);
            return "job/form";
        }

        jobDao.save(job);

        return "redirect:/job/list";
    }

    @RequestMapping("/job/delete")
    public String delete(@RequestParam("id") Long id) {
        log.info("WEB request to delete Job : {}", id);
        jobDao.delete(id);
        return "redirect:/job/list";
    }

    /**
     * Handle request to download a PDF document
     */
    @RequestMapping(value = "/job/downloadPDF", method = RequestMethod.GET)
    public ModelAndView downloadPdf(@RequestParam("id") Long id) {
        log.info("WEB request to report pdf Job : {}", id);
        Job job = jobDao.findOne(id);
        log.info("Job : {}", job);
        // create some sample data
        List<Book> listBooks = new ArrayList<Book>();
        listBooks.add(new Book("Spring in Action", "Craig Walls", "1935182358",
                "June 29th 2011", 31.98F));
        listBooks.add(new Book("Spring in Practice", "Willie Wheeler, Joshua White",
                "1935182056", "May 16th 2013", 31.95F));
        listBooks.add(new Book("Pro Spring 3",
                "Clarence Ho, Rob Harrop", "1430241071", "April 18th 2012", 31.85F));
        listBooks.add(new Book("Spring Integration in Action", "Mark Fisher", "1935182439",
                "September 26th 2012", 28.73F));

        // return a view which will be resolved by an excel view resolver
        return new ModelAndView(new PDFBuilder(), "listBooks", listBooks);
    }

}