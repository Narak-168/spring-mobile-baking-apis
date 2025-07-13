package kh.edu.istad.mobileapi.repository;

import kh.edu.istad.mobileapi.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KYCRepository extends JpaRepository
        <KYC, Integer> {
}
