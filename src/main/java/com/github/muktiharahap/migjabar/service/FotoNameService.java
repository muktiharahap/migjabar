package com.github.muktiharahap.migjabar.service;

import com.github.muktiharahap.migjabar.domain.FotoName;
import com.github.muktiharahap.migjabar.repository.FotoNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author mukti on 9/21/2017.
 */
@Service
@Transactional
public class FotoNameService {

    @Autowired
    FotoNameRepository fotoNameRepository;

    public FotoName simpan(String name, Date createdDate) {

        FotoName fotoName = new FotoName();

        fotoName.setName(name);
        fotoName.setCreatedDate(createdDate);

        fotoNameRepository.save(fotoName);

        return fotoName;
    }

}
