package kh.edu.istad.mobileapi.repository;

import kh.edu.istad.mobileapi.domain.Segment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SegmentRepository extends JpaRepository<Segment, Integer> {
    Optional<Segment> getSegmentByName(String segmentName);
}
