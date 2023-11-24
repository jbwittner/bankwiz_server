package fr.bankwiz.server.infrastructure.spi.database.entity;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import fr.bankwiz.server.domain.model.data.Group;
import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class GroupRelatedEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID", columnDefinition = "varchar(36)", nullable = false, updatable = false, insertable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(
            name = "GROUP_ID",
            nullable = false,
            foreignKey = @jakarta.persistence.ForeignKey(name = "FK_GROUPRELATEDENTITY_GROUP"))
    protected Group group;
}
