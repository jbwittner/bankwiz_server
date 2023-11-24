package fr.bankwiz.server.infrastructure.spi.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BANK_ACCOUNT")
public class BankAccountEntity extends GroupRelatedEntity {

    @Column(name = "NAME", nullable = false, length = 60)
    private String name;

    @Column(name = "BASE_AMOUNT", nullable = false)
    private Integer baseAmountDecimal;
}
