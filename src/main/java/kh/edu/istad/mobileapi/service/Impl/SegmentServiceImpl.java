package kh.edu.istad.mobileapi.service.Impl;


import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import kh.edu.istad.mobileapi.domain.Segment;
import kh.edu.istad.mobileapi.repository.SegmentRepository;
import kh.edu.istad.mobileapi.service.SegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SegmentServiceImpl implements SegmentService {

    private final SegmentRepository segmentRepository;

    @PostConstruct
    @Transactional
    public void initDefaultSegments() {
        List<Segment> defaultSegments = Arrays.asList(
                createSegment("Regular", Double.valueOf(5000)),
                createSegment("Silver", Double.valueOf(10000)),
                createSegment("Gold", Double.valueOf(50000))
        );

        segmentRepository.saveAll(defaultSegments);
    }

    @Override
    public Segment createSegment(String segName, Double overLimit) {
        Segment segment = new Segment();
        segment.setName(segName);
        segment.setOverLimit(overLimit);
        segment.setIsDeleted(false);
        return segment;
    }
}
