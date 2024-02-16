package fr.bankwiz.server.infrastructure.spi.database.entity;

import java.util.UUID;

import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity.GroupRightEntityEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "BANK_ACCOUNT")
public class BankAccountEntity extends GroupRelatedEntity {

    @Builder
    public BankAccountEntity(UUID id, GroupEntity groupEntity, String bankAccountName, Integer baseAmountDecimal, CurrencyEntityEnum currencyEntityEnum) {
        super(id, groupEntity);
        this.bankAccountName = bankAccountName;
        this.baseAmountDecimal = baseAmountDecimal;
        this.currencyEntityEnum = currencyEntityEnum;
    }

    @Column(name = "BANK_ACCOUNT_NAME", nullable = false, length = 60)
    private String bankAccountName;

    @Column(name = "BASE_AMOUNT", nullable = false)
    private Integer baseAmountDecimal;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY", columnDefinition = "ENUM('EUR','USD')", nullable = false)
    private CurrencyEntityEnum currencyEntityEnum;

    public enum CurrencyEntityEnum {
        EUR,
        USD
    }
}
