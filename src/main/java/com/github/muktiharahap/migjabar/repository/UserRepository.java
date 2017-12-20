package com.github.muktiharahap.migjabar.repository;

import com.github.muktiharahap.migjabar.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author mukti on 9/30/2017.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>  {

    User findOneByNikAndPassword(String nik, String password);

    User findOneByNik(String nik);

    Page<User> findOneByNik(String nik, Pageable pageable);

    Page<User> findByLoginContainingIgnoreCase(String login, Pageable pageable);

    User findByEmail(String email);
}
