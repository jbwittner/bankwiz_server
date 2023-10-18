package fr.bankwiz.server.infrastructure.spi.database.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Table(name = "USER_ACCOUNT")
public class UserEntity {
    
    @Id
    @Column(name = "USER_ACCOUNT_ID", nullable = false, updatable = false, insertable = false)
    private UUID userId;
    
    @Column(name = "AUTH_ID", nullable = false, updatable = false)
    private String authId;

    @Column(name = "EMAIL", nullable = false)
    private String email;
}
