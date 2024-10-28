package fr.bankwiz.server.infrastructure.spijpa.spi.database.mapper;

import org.mapstruct.Mapper;

import fr.bankwiz.server.domain.model.data.SimpleData;
import fr.bankwiz.server.infrastructure.spijpa.spi.database.entity.SimpleEntity;

@Mapper(componentModel = "spring")
public interface JPASimpleDataMapper {

    SimpleData toSimpleData(SimpleEntity simpleEntity);
}
