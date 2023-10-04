package fr.bankwiz.server.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

/** Class of the User account */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_ACCOUNT")
public class User {

    @Id
    @Column(name = "USER_ACCOUNT_ID", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID userAccountId;

    @Column(name = "AUTH_ID", nullable = false, updatable = false)
    protected String authId;

    @Column(name = "EMAIL", nullable = false)
    private String email;

}
