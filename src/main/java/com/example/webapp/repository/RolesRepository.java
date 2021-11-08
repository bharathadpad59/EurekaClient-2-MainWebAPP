package com.example.webapp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.webapp.entities.Logins;

@Repository
@Transactional
public interface RolesRepository extends JpaRepository<Logins,Integer> {

	@Query(value = "select name from logins",nativeQuery = true)
	List<String> getAllNames();
}
