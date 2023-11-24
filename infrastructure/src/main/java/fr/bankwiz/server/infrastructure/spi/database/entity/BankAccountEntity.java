package fr.bankwiz.server.infrastructure.spi.database.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "BANK_ACCOUNT")
public class BankAccountEntity extends GroupRelatedEntity {

    @Builder
    public BankAccountEntity(UUID id, GroupEntity groupEntity, String name, Integer baseAmountDecimal) {
        super(id, groupEntity);
        this.name = name;
        this.baseAmountDecimal = baseAmountDecimal;
    }

    @Column(name = "NAME", nullable = false, length = 60)
    private String name;

    @Column(name = "BASE_AMOUNT", nullable = false)
    private Integer baseAmountDecimal;
}
