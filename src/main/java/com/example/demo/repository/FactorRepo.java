package com.example.demo.repository;

import com.example.demo.model.Factor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FactorRepo extends JpaRepository<Factor, Long> {
    public Factor findByOwner( @Param("owner") String owner);
}
