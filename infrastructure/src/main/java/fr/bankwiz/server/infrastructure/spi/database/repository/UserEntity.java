package fr.bankwiz.server.infrastructure.spi.database.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_ACCOUNT")
public class UserEntity {

    @Id
    @Column(name = "ID", nullable = false, updatable = false, insertable = false)
    private UUID id;

    @Column(name = "AUTH_ID", nullable = false, updatable = false)
    private String authId;

    @Column(name = "EMAIL", nullable = false)
    private String email;
}