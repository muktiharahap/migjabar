package com.github.muktiharahap.migjabar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.muktiharahap.migjabar.domain.Role;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByRole(String role);

	Role findOneByRole(String admin);
}
