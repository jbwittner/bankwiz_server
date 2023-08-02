package fr.bankwiz.server.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
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
@Table(
        name = "GROUP_ACCOUNT")
public class BankAccount extends GroupRelatedEntity {

    @Column(name = "NAME", nullable = false, length = 60)
    private String name;

    @Column(name = "BASE_AMOUNT", nullable = false)
    private Integer baseAmountDecimal;
}