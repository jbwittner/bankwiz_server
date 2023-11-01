package fr.bankwiz.server.infrastructure.spi.database.entity;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "GROUP_RIGHT")
public class GroupRightEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(
            name = "GROUP_RIGHT_ID",
            columnDefinition = "varchar(36)",
            nullable = false,
            updatable = false,
            insertable = false)
    private UUID groupRightId;

    @ManyToOne
    @JoinColumn(
            name = "GROUP_ID",
            nullable = false,
            foreignKey = @jakarta.persistence.ForeignKey(name = "FK_GROUP_RIGHT_GROUP"))
    private GroupEntity groupEntity;

    @ManyToOne
    @JoinColumn(
            name = "USER_ID",
            nullable = false,
            foreignKey = @jakarta.persistence.ForeignKey(name = "FK_GROUP_RIGHT_USER"))
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "GROUP_RIGHT", columnDefinition = "ENUM('READ','WRITE','ADMIN')", nullable = false)
    private GroupRightEntityEnum groupRightEntityEnum;

    public enum GroupRightEntityEnum {
        READ,
        WRITE,
        ADMIN
    }
}
