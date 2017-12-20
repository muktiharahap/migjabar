package com.github.muktiharahap.migjabar.service;

import com.github.muktiharahap.migjabar.repository.AttachmentRepository;
import com.github.muktiharahap.migjabar.domain.Attachment;
import com.github.muktiharahap.migjabar.domain.FotoName;
import com.github.muktiharahap.migjabar.domain.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author mukti on 9/21/2017.
 */
@Service
@Transactional
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    public Attachment simpan(Job job, FotoName fotoName, String fotoPath, Date createdDate, Double latitude, Double longtitude) {
        Attachment attachment = new Attachment();

        attachment.setJob(job);
        attachment.setFotoName(fotoName);
        attachment.setFotoPath(fotoPath);
        attachment.setCreatedDate(createdDate);
        attachment.setLatitude(latitude);
        attachment.setLongtitude(longtitude);

        attachmentRepository.save(attachment);

        return attachment;
    }
}
