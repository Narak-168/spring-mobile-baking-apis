package kh.edu.istad.mobileapi.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tranType_id")
    private TransactionType tranType;


    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiver_is")
    private Account receiver;

    @JoinColumn(nullable = false)
    private Double amount;

    @JoinColumn(columnDefinition = "TEXT")
    private String remark;

}
