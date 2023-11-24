package fr.bankwiz.server.infrastructure.spi.database.entity;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@Entity
@AllArgsConstructor
@Table(name = "USER_ACCOUNT")
@EqualsAndHashCode
public class UserEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID", columnDefinition = "varchar(36)", nullable = false, updatable = false, insertable = false)
    private UUID id;

    @Column(name = "AUTH_ID", nullable = false, updatable = false)
    private String authId;

    @Column(name = "EMAIL", nullable = false)
    private String email;
}
