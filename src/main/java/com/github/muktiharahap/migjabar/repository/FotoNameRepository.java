package com.github.muktiharahap.migjabar.repository;

import com.github.muktiharahap.migjabar.domain.FotoName;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by smg14005 on 9/21/2017.
 */
public interface FotoNameRepository extends PagingAndSortingRepository<FotoName, Long> {
    FotoName findOneByName(String name);
}
