package com.server.loan.calculator.repository;

import com.server.loan.calculator.model.AdminData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminData, Integer> {
}
