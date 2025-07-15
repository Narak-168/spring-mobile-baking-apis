package kh.edu.istad.mobileapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateCustomerRequest(
        @NotBlank(message = "Full Name is required")
        String fullName,

        @NotBlank(message = "Gender is required")
        String gender,

        @Email
        String email,
        String phone,
        String remark,


        //homework-V2
        @NotBlank(message = "National Card ID is required")
        String nationalCardID,

        @NotBlank(message = "Segment is required")
        String segmentName
) {
}