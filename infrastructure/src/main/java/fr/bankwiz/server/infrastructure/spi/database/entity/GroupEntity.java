package fr.bankwiz.server.infrastructure.spi.database.entity;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_GROUP")
@EqualsAndHashCode
public class GroupEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(
            name = "GROUP_ID",
            columnDefinition = "varchar(36)",
            nullable = false,
            updatable = false,
            insertable = false)
    private UUID groupId;

    @Column(name = "GROUP_NAME", nullable = false)
    private String groupName;
}
