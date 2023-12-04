package fr.bankwiz.server.infrastructure.integrationtest.api.groupcontroller;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.openapi.model.GroupCreationRequest;
import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity.GroupRightEntityEnum;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.GroupEntityRepository;
import fr.bankwiz.server.infrastructure.spi.database.repository.GroupRightEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

import static io.restassured.RestAssured.given;

class CreateGroupTest extends InfrastructureIntegrationTestBase {

    @Autowired
    private GroupEntityRepository groupEntityRepository;

    @Autowired
    private GroupRightEntityRepository groupRightEntityRepository;

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() throws Exception {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final GroupCreationRequest groupCreationRequest =
                new GroupCreationRequest(this.faker.space().constellation());

        final GroupIndexDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .body(groupCreationRequest)
                .post("/group")
                .as(GroupIndexDTO.class);

        Assertions.assertEquals(groupCreationRequest.getGroupName(), response.getGroupName());

        Optional<GroupEntity> optionalGroupEntity = this.groupEntityRepository.findById(response.getGroupId());

        Assertions.assertAll(
                () -> Assertions.assertTrue(optionalGroupEntity.isPresent()),
                () -> Assertions.assertEquals(
                        groupCreationRequest.getGroupName(),
                        optionalGroupEntity.get().getGroupName()));

        final GroupEntity groupEntity = optionalGroupEntity.get();

        List<GroupRightEntity> groupRightEntities = this.groupRightEntityRepository.findByGroupEntity(groupEntity);

        final UserEntity userEntity = UserTransformer.toUserEntity(user);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, groupRightEntities.size()),
                () -> Assertions.assertEquals(
                        groupEntity, groupRightEntities.get(0).getGroupEntity()),
                () -> Assertions.assertEquals(
                        userEntity, groupRightEntities.get(0).getUserEntity()),
                () -> Assertions.assertEquals(
                        GroupRightEntityEnum.ADMIN.name(),
                        groupRightEntities.get(0).getGroupRightEntityEnum().name()));
    }
}
