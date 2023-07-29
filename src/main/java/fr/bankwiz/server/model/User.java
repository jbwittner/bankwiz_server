package fr.bankwiz.server.model;

import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "USER_ID", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer userId;

    @Column(name = "AUTH_ID", nullable = false, updatable = false)
    protected String authId;

    @Column(name = "FIRST_NAME", nullable = false, length = 60)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 60)
    private String lastName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<GroupRight> groupRights = new ArrayList<>();

    public void addGroupRight(GroupRight groupRight) {
        this.groupRights.add(groupRight);
        groupRight.setUser(this);
    }

    public boolean removeGroupRight(GroupRight groupRight) {
        return this.groupRights.remove(groupRight);
    }
}
