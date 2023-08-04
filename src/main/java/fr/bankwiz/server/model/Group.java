package fr.bankwiz.server.model;

import java.util.ArrayList;
import java.util.List;

import fr.bankwiz.server.exception.UserNoReadRightException;
import fr.bankwiz.server.exception.UserNoWriteRightException;
import fr.bankwiz.server.exception.UserNotAdminException;
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
    @Column(name = "USER_GROUP_ID", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer userGroupId;

    @Column(name = "NAME", nullable = false, length = 60)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<GroupRight> groupRights = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts = new ArrayList<>();

    public void addGroupRight(GroupRight groupRight) {
        this.groupRights.add(groupRight);
        groupRight.setGroup(this);
    }

    public void addBankAccount(BankAccount bankAccount) {
        this.bankAccounts.add(bankAccount);
        bankAccount.setGroup(this);
    }

    public void removeGroupRight(GroupRight groupRight) {
        this.groupRights.remove(groupRight);
    }

    public void removeBankAccount(BankAccount bankAccount) {
        this.bankAccounts.remove(bankAccount);
    }

    public boolean hasRight(User user, GroupRightEnum right) {
        return this.groupRights.stream()
                .filter(p -> p.getUser().getUserAccountId().equals(user.userAccountId))
                .anyMatch(p -> p.getGroupRightEnum().equals(right));
    }

    public boolean hasAnyRight(User user) {
        return this.groupRights.stream()
                .anyMatch(p -> p.getUser().getUserAccountId().equals(user.userAccountId));
    }

    public boolean isAdmin(User user) {
        return hasRight(user, GroupRightEnum.ADMIN);
    }

    public boolean canWrite(User user) {
        return hasRight(user, GroupRightEnum.WRITE) || isAdmin(user);
    }

    public boolean canRead(User user) {
        return hasRight(user, GroupRightEnum.READ) || canWrite(user);
    }

    public void checkCanWrite(User user) {
        if (!this.canWrite(user)) {
            throw new UserNoWriteRightException(user, this);
        }
    }

    public void checkCanRead(User user) {
        if (!this.canRead(user)) {
            throw new UserNoReadRightException(user, this);
        }
    }

    public void checkIsAdmin(User user) {
        if (!this.isAdmin(user)) {
            throw new UserNotAdminException(user, this);
        }
    }
}
