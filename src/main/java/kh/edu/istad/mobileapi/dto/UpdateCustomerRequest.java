package kh.edu.istad.mobileapi.dto;

public record UpdateCustomerRequest(
        String fullName,
        String gender,
        String remark
) {
}
