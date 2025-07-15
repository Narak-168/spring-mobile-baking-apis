package kh.edu.istad.mobileapi.dto;
public record AccountResponse(
        String actNo,
        String actType,
        String actCurrency,
        Double balance,
        Boolean isDeleted,
        Double overLimit
) {
}
