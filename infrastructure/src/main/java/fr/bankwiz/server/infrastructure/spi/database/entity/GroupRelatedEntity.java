package fr.bankwiz.server.infrastructure.spi.database.entity;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EqualsAndHashCode
public abstract class GroupRelatedEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID", columnDefinition = "varchar(36)", nullable = false, updatable = false, insertable = false)
    protected UUID id;

    @ManyToOne
    @JoinColumn(
            name = "GROUP_ID",
            nullable = false,
            foreignKey = @jakarta.persistence.ForeignKey(name = "FK_GROUPRELATEDENTITY_GROUP"))
    protected GroupEntity groupEntity;
}
