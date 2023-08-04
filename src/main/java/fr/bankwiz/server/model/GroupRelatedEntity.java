package fr.bankwiz.server.model;

import fr.bankwiz.server.exception.IncompatibleGroupException;
import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class GroupRelatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false, insertable = false, unique = true)
    protected Integer id;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_GROUP"))
    protected Group group;

    public void checkCanAssociateWith(final GroupRelatedEntity otherEntity) {
        final Group targetGroup = otherEntity.getGroup();

        if (!this.group.equals(targetGroup)) {
            throw new IncompatibleGroupException(this.group, targetGroup);
        }
    }

    public void checkCanWriteGroup(User user) {
        this.group.checkCanWrite(user);
    }

    public void checkCanReadGroup(User user) {
        this.group.checkCanRead(user);
    }

    public void checkIsAdminOfGroup(User user) {
        this.group.checkIsAdmin(user);
    }
}
