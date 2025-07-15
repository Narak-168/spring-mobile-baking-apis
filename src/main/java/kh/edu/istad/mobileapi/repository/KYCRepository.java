package kh.edu.istad.mobileapi.repository;

import kh.edu.istad.mobileapi.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KYCRepository extends JpaRepository<KYC, Integer> {
    Boolean existsByNationalCardId(String nationalCardId);
    Optional<KYC> findByNationalCardId(String nationalCardId);
}
