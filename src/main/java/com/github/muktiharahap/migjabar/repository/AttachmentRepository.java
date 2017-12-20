package com.github.muktiharahap.migjabar.repository;

import com.github.muktiharahap.migjabar.domain.Attachment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by smg14005 on 9/21/2017.
 */
@Repository
public interface AttachmentRepository extends PagingAndSortingRepository<Attachment, Long> {
}
