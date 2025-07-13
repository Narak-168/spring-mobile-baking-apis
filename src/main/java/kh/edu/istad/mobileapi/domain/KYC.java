package kh.edu.istad.mobileapi.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class KYC {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; //UUID
    private String nationalCardId;
    private Boolean isVerified;
    private Boolean isDeleted;


    @OneToOne(mappedBy = "kyc")
    private Customer customer;
}
