package com.github.muktiharahap.migjabar.web.rest;

import com.github.muktiharahap.migjabar.domain.FotoName;
import com.github.muktiharahap.migjabar.domain.Job;
import com.github.muktiharahap.migjabar.domain.User;
import com.github.muktiharahap.migjabar.domain.enumeration.Mig;
import com.github.muktiharahap.migjabar.domain.enumeration.Sow;
import com.github.muktiharahap.migjabar.repository.FotoNameRepository;
import com.github.muktiharahap.migjabar.repository.UserRepository;
import com.github.muktiharahap.migjabar.service.AttachmentService;
import com.github.muktiharahap.migjabar.service.FotoNameService;
import com.github.muktiharahap.migjabar.service.ImageService;
import com.github.muktiharahap.migjabar.service.JobService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.github.muktiharahap.migjabar.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author mukti
 */
@RestController
@RequestMapping("/api")
public class JobResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobResource.class);

    @Autowired
    FotoNameRepository fotoNameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private JobService jobService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private FotoNameService fotoNameService;

	@RequestMapping(value = "/job/simpan", method = RequestMethod.POST)
    public Map<String, Object> simpan(@RequestBody Map<String, Object> data) {

        Gson gson = new Gson();

        Type typeMap = new TypeToken<Map<String, ?>>(){}.getType();
        Type typeArrayList = new TypeToken<ArrayList>(){}.getType();

        Object dataJob = data.get(Constants.TAG_DATAMIG);
        Map<String, ?> jsonJob = gson.fromJson(gson.toJson(dataJob), typeMap);

        Map<String, Object> hasil = new HashMap<>();

        User user = userRepository.findOneByNik(jsonJob.get(Constants.TAG_NIK).toString());
        if (user!=null) {
            Date createdDate = stringToDate(jsonJob.get(Constants.TAG_DATECREATED).toString());

            Job job = jobService.simpan(jsonJob.get(Constants.TAG_UUIDMIG).toString(), jsonJob.get(Constants.TAG_NOTEL).toString(), Sow.valueOf(jobService.convertSow(jsonJob.get(Constants.TAG_SOW).toString())), Mig.valueOf(jsonJob.get(Constants.TAG_MIG).toString()), jsonJob.get(Constants.TAG_SC).toString(), jsonJob.get(Constants.TAG_STO).toString(), jsonJob.get(Constants.TAG_ODP).toString(), jsonJob.get(Constants.TAG_PORT).toString(), jsonJob.get(Constants.TAG_KATEGORI).toString(), createdDate, jsonJob.get(Constants.TAG_NOSPEEDY).toString(), jsonJob.get(Constants.TAG_DCKABEL).toString(), jsonJob.get(Constants.TAG_SN).toString());

            Object foto = jsonJob.get(Constants.TAG_FOTO);
            ArrayList attachments = gson.fromJson(gson.toJson(foto), typeArrayList);

            for (Object attachment : attachments) {
                Map<String, ?> mapAttachment = gson.fromJson(gson.toJson(attachment), typeMap);

                FotoName fotoName = fotoNameRepository.findOneByName(mapAttachment.get(Constants.TAG_FOTONAME).toString());
                if (fotoName == null) {
                    fotoName = fotoNameService.simpan(mapAttachment.get(Constants.TAG_FOTONAME).toString(), createdDate);
                }

                // save files
                imageService.moveFile(mapAttachment.get(Constants.TAG_FOTOSTRING64).toString(), mapAttachment.get(Constants.TAG_FOTOPATH).toString());

                Date createdDateFoto = stringToDate(mapAttachment.get(Constants.TAG_DATECREATEDFOTO).toString());
                // save attachments
                attachmentService.simpan(job, fotoName, mapAttachment.get(Constants.TAG_FOTOPATH).toString(), createdDateFoto, Double.valueOf(mapAttachment.get(Constants.TAG_LATITUDE).toString()), Double.valueOf(mapAttachment.get(Constants.TAG_LONGTITUDE).toString()));
            }

            hasil.put("success", true);
            LOGGER.info("Hasil : " + hasil);
        } else {
            hasil.put("success", false);
            hasil.put("message", "Not found user "+jsonJob.get(Constants.TAG_NIK).toString());
            LOGGER.info("Hasil : " + hasil);
        }
        return hasil;
    }

    private Date stringToDate(String tgl) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(tgl);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}