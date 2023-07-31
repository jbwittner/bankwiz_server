package fr.bankwiz.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GROUP_RIGHT")
public class GroupRight {

    @Id
    @Column(name = "RIGHT_ID", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rightId;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_GROUP"))
    private Group group;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_USER"))
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "GROUP_RIGHT", columnDefinition = "ENUM('READ','WRITE','ADMIN')", nullable = false)
    private GroupRightEnum groupRightEnum;

    public enum GroupRightEnum {
        READ,
        WRITE,
        ADMIN
    }
}
