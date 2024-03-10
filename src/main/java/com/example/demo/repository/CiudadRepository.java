package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Ciudad;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, String> {
}
