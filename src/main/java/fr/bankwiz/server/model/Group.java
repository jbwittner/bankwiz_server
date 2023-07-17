package fr.bankwiz.server.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

/** Class of the User account */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_GROUP")
public class Group {

    @Id
    @Column(name = "GROUP_ID", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer groupId;

    @Column(name = "GROUP_NAME", nullable = false, length = 60)
    private String groupName;

    @Builder.Default
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupRight> groupRights = new ArrayList<>();
}
