package fr.bankwiz.server.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @Column(name = "TRANSACTION_ID", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer transactionId;

    @Digits(integer = 38, fraction = 4)
    @Column(name = "AMOUNT", nullable = false)
    private Integer amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "BANK_ACCOUNT_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_TRANSACTION_BANK_ACCOUNT"))
    protected BankAccount bankAccount;
}