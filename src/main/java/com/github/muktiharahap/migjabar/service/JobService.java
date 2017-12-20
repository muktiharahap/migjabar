package com.github.muktiharahap.migjabar.service;

import com.github.muktiharahap.migjabar.domain.Job;
import com.github.muktiharahap.migjabar.domain.enumeration.Mig;
import com.github.muktiharahap.migjabar.domain.enumeration.Sow;
import com.github.muktiharahap.migjabar.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author mukti on 9/21/2017.
 */
@Service
@Transactional
public class JobService {

    @Autowired
    JobRepository jobDao;

    public Job simpan(String uuidMig, String notel, Sow sow, Mig mig, String sc, String sto, String odp, String port, String kategori, Date createDate, String nospeedy, String dckabel, String sn) {

        Job job = new Job();

        job.setUuidMig(uuidMig);
        job.setNotel(notel);
        job.setSow(sow);
        job.setMig(mig);
        job.setSc(sc);
        job.setSto(sto);
        job.setOdp(odp);
        job.setPort(port);
        job.setKategori(kategori);
        job.setCreatedDate(createDate);
        job.setNospeedy(nospeedy);
        job.setDckabel(dckabel);
        job.setSn(sn);

        jobDao.save(job);

        return job;
    }

    public String convertSow(String sow_) {
        String sow = "";
        switch (sow_) {
            case "1P":
                sow = "IP";
                break;
            case "2P":
                sow = "IIP";
                break;
            case "3P":
                sow = "IIIP";
                break;
        }
        return sow;
    }
}
