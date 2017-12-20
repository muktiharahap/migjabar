package com.github.muktiharahap.migjabar.repository;

import com.github.muktiharahap.migjabar.domain.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mukti harahap  <skylae.mukti at gmail.com>
 * @since 20 Sept 2017
 */
@Repository
public interface JobRepository extends PagingAndSortingRepository<Job, Long> {
    Job findOneByStatus(String status);
    Page<Job> findByNotelContainingIgnoreCase(String notel, Pageable pageable);
}
