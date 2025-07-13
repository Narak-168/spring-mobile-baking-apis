package kh.edu.istad.mobileapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import kh.edu.istad.mobileapi.domain.AccountType;
import lombok.*;

@Builder
public record CreateAccountRequest(

        @NotBlank(message = "Account number is required")
        String actNo,

        @NotBlank(message = "Currency is required")
        String actCurrency,

        @NotNull(message = "Customer Phone Number is required")
        String phoneNumber
){
}

