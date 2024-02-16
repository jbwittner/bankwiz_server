package fr.bankwiz.server.infrastructure.integrationtest.api;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.openapi.model.AddUserGroupRequest;
import fr.bankwiz.openapi.model.GroupCreationRequest;
import fr.bankwiz.openapi.model.GroupDetailsDTO;
import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.openapi.model.UserGroupRightDTO;
import fr.bankwiz.openapi.model.UserGroupRightEnum;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupRightEntity.GroupRightEntityEnum;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.GroupEntityRepository;
import fr.bankwiz.server.infrastructure.spi.database.repository.GroupRightEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;
import io.restassured.common.mapper.TypeRef;

import static io.restassured.RestAssured.given;

class GroupControllerTest extends InfrastructureIntegrationTestBase {

    @Autowired
    private GroupEntityRepository groupEntityRepository;

    @Autowired
    private GroupRightEntityRepository groupRightEntityRepository;

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void createGroup() throws Exception {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final GroupCreationRequest groupCreationRequest =
                new GroupCreationRequest(this.faker.space().constellation());

        final GroupIndexDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .body(groupCreationRequest)
                .post("/group")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
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

    @Test
    void getUserGroups() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final GroupRightDomain groupRight1 = this.factory.getGroupRight(user, GroupRightEnum.ADMIN);
        final GroupRightDomain groupRight2 = this.factory.getGroupRight(user, GroupRightEnum.ADMIN);
        final GroupRightDomain groupRight3 = this.factory.getGroupRight(user, GroupRightEnum.ADMIN);

        final List<GroupIndexDTO> response = given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .get("/group/groups")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<GroupIndexDTO>>() {});

        Assertions.assertAll(() -> Assertions.assertEquals(3, response.size()), () -> {
            response.stream()
                    .filter(groupIndexDTO -> groupIndexDTO
                            .getGroupId()
                            .equals(groupRight1.getGroup().getId()))
                    .findAny()
                    .orElseThrow();
            response.stream()
                    .filter(groupIndexDTO -> groupIndexDTO
                            .getGroupId()
                            .equals(groupRight2.getGroup().getId()))
                    .findAny()
                    .orElseThrow();
            response.stream()
                    .filter(groupIndexDTO -> groupIndexDTO
                            .getGroupId()
                            .equals(groupRight3.getGroup().getId()))
                    .findAny()
                    .orElseThrow();
        });
    }

    @Test
    void getGroupDetails() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final GroupRightDomain groupRight = this.factory.getGroupRight(user, GroupRightEnum.ADMIN);

        final GroupDomain group = groupRight.getGroup();

        final GroupDetailsDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .get("/group/" + group.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(GroupDetailsDTO.class);

        Assertions.assertEquals(group.getId(), response.getGroupId());
    }

    @Test
    void addUserGroup() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final GroupRightDomain groupRight = this.factory.getGroupRight(user, GroupRightEnum.ADMIN);

        final GroupDomain group = groupRight.getGroup();
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);

        final User anotherUser = this.factory.getUser();

        final AddUserGroupRequest addUserGroupRequest =
                new AddUserGroupRequest(anotherUser.getId(), UserGroupRightEnum.READ);

        final String path = "/group/" + group.getId() + "/user";

        final UserGroupRightDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .body(addUserGroupRequest)
                .post(path)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(UserGroupRightDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(addUserGroupRequest.getRight(), response.getRight()),
                () -> Assertions.assertEquals(
                        anotherUser.getId(), response.getUser().getId()));

        final List<GroupRightEntity> groupRightEntities =
                this.groupRightEntityRepository.findByGroupEntity(groupEntity);

        Assertions.assertEquals(2, groupRightEntities.size());

        final Optional<GroupRightEntity> optional = groupRightEntities.stream()
                .filter(gre -> gre.getUserEntity().getId().equals(anotherUser.getId()))
                .findAny();

        Assertions.assertTrue(optional.isPresent());

        final GroupRightEntity groupRightEntity = optional.get();

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        groupEntity.getId(), groupRightEntity.getGroupEntity().getId()),
                () -> Assertions.assertEquals(
                        anotherUser.getId(), groupRightEntity.getUserEntity().getId()),
                () -> Assertions.assertEquals(
                        addUserGroupRequest.getRight().name(),
                        groupRightEntity.getGroupRightEntityEnum().name()));
    }

    @Test
    void deleteUserFromGroup() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);
        final User anotherUser = this.factory.getUser();

        final GroupDomain group = this.factory.getGroup();
        this.factory.getGroupRight(group, user, GroupRightEnum.ADMIN);
        this.factory.getGroupRight(group, anotherUser, GroupRightEnum.WRITE);

        final String path = "/group/" + group.getId() + "/user/" + anotherUser.getId();

        given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .delete(path)
                .then()
                .assertThat()
                .statusCode(200);

        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);

        final List<GroupRightEntity> groupRightEntities =
                this.groupRightEntityRepository.findByGroupEntity(groupEntity);

        Assertions.assertEquals(1, groupRightEntities.size());
    }

    @Test
    void deleteGroup() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final GroupDomain group = this.factory.getGroup();
        this.factory.getGroupRight(group, user, GroupRightEnum.ADMIN);

        final String path = "/group/" + group.getId();

        given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .delete(path)
                .then()
                .assertThat()
                .statusCode(200);

        final boolean result = this.groupEntityRepository.existsById(group.getId());

        Assertions.assertFalse(result);
    }
}
