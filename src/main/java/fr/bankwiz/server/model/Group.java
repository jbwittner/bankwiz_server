package fr.bankwiz.server.model;

import java.util.ArrayList;
import java.util.List;

import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
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

    public void addGroupRight(GroupRight groupRight) {
        this.groupRights.add(groupRight);
        groupRight.setGroup(this);
    }

    public boolean isAdmin(User user) {
        final var resultFilter = this.groupRights.stream()
                .filter(p -> p.getUser().getUserId().equals(user.userId))
                .findFirst();
        boolean result = false;
        if (resultFilter.isPresent()) {
            final GroupRight right = resultFilter.get();
            if (GroupRightEnum.ADMIN.equals(right.getGroupRightEnum())) {
                result = true;
            }
        }
        return result;
    }

    public boolean canWrite(User user) {
        final var resultFilter = this.groupRights.stream()
                .filter(p -> p.getUser().getUserId().equals(user.userId))
                .findFirst();
        boolean result = false;
        if (resultFilter.isPresent()) {
            final GroupRight right = resultFilter.get();
            if (GroupRightEnum.WRITE.equals(right.getGroupRightEnum())
                    || GroupRightEnum.ADMIN.equals(right.getGroupRightEnum())) {
                result = true;
            }
        }
        return result;
    }

    public boolean canRead(User user) {
        final var resultFilter = this.groupRights.stream()
                .filter(p -> p.getUser().getUserId().equals(user.userId))
                .findFirst();
        boolean result = false;
        if (resultFilter.isPresent()) {
            final GroupRight right = resultFilter.get();
            if (GroupRightEnum.WRITE.equals(right.getGroupRightEnum())
                    || GroupRightEnum.ADMIN.equals(right.getGroupRightEnum())
                    || GroupRightEnum.READ.equals(right.getGroupRightEnum())) {
                result = true;
            }
        }
        return result;
    }
}
