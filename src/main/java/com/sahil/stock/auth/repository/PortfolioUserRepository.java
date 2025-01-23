package com.sahil.stock.auth.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahil.stock.auth.model.PortfolioUser;

@Repository
public interface PortfolioUserRepository extends JpaRepository<PortfolioUser, UUID> {
}
