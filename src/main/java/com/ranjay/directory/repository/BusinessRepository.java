package com.ranjay.directory.repository;

import com.ranjay.directory.model.Business;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business,Long> {
    Page<Business> findByNameLike(String name, Pageable pageable);
}