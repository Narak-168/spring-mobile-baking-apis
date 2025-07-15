package kh.edu.istad.mobileapi.service;

import kh.edu.istad.mobileapi.domain.Segment;

public interface SegmentService {
    Segment createSegment(String segName, Double overLimit);
}
