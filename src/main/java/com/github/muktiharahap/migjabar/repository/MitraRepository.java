package com.github.muktiharahap.migjabar.repository;

import com.github.muktiharahap.migjabar.domain.Mitra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mukti on 9/30/2017.
 */
@Repository
public interface MitraRepository extends PagingAndSortingRepository<Mitra, Long> {
    Page<Mitra> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
