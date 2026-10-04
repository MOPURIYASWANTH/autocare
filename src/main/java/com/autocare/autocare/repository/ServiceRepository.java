package com.autocare.autocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autocare.autocare.entity.AutoService;

public interface ServiceRepository extends JpaRepository<AutoService, Long> {

}