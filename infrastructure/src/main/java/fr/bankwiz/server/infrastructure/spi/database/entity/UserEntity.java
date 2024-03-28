package fr.bankwiz.server.infrastructure.spi.database.entity;

import java.util.UUID;

import fr.bankwiz.server.domain.model.UserDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_ACCOUNT")
public class UserEntity {

    public UserEntity(final UserDomain userDomain) {
        this(userDomain.id(), userDomain.authId(), userDomain.email());
    }

    public UserDomain toUserDomain() {
        return new UserDomain(this.id, this.authId, this.email);
    }

    @Id
    @Column(name = "ID", nullable = false, updatable = false, insertable = false)
    private UUID id;

    @Column(name = "AUTH_ID", nullable = false, updatable = false)
    private String authId;

    @Column(name = "EMAIL", nullable = false)
    private String email;
}
