package com.github.muktiharahap.migjabar.service;

import com.github.muktiharahap.migjabar.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpringDataJpaUserDetailsService implements UserDetailsService {

  private static final Logger log = LoggerFactory.getLogger(SpringDataJpaUserDetailsService.class);

  private final UserRepository repository;

  @Autowired
  public SpringDataJpaUserDetailsService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    log.info("Authenticating {}", name);
    com.github.muktiharahap.migjabar.domain.User user = this.repository.findOneByNik(name);
    List<GrantedAuthority> grantedAuthorities = user.getRoles().stream().map(authority -> new SimpleGrantedAuthority(authority.getRole())).collect(Collectors.toList());
    return new org.springframework.security.core.userdetails.User(user.getNik(), user.getPassword(), grantedAuthorities);
  }

}