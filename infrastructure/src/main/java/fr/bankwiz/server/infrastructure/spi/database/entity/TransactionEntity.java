package fr.bankwiz.server.infrastructure.spi.database.entity;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TRANSACTION")
@EqualsAndHashCode
public class TransactionEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID", columnDefinition = "varchar(36)", nullable = false, updatable = false, insertable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(
            name = "BANK_ACCOUNT_ID",
            nullable = false,
            foreignKey = @jakarta.persistence.ForeignKey(name = "FK_TRANSACTION_BANKACCOUNT"))
    private BankAccountEntity bankAccountEntity;

    @Column(name = "COMMENT", nullable = false, length = 255)
    private String comment;

    @Column(name = "DECIMAL_AMOUNT", nullable = false)
    private Integer decimalAmount;
}
