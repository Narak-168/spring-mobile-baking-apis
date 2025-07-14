package kh.edu.istad.mobileapi.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true, nullable = false, length = 32)
    private String actNo;

    @ManyToOne
    @JoinColumn(name = "actType_id")
    private AccountType actType;

    @Column(nullable=false)
    private String accountCurrency;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "custom_id") //Change the name from customer_id --> custom_id
    private  Customer customer;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> senderTransactions;

    @OneToMany(mappedBy = "receiver")
    private List<Transaction> receiverTransactions;
}
