package fr.bankwiz.server.infrastructure.transformer.grouptransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;

class ToGroupIndexDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final Group group = this.factory.getGroup();
        final GroupIndexDTO groupIndexDTO = GroupTransformer.toGroupIndexDTO(group);
        Assertions.assertAll(
                () -> Assertions.assertEquals(group.getGroupName(), groupIndexDTO.getGroupName()),
                () -> Assertions.assertEquals(group.getGroupId(), groupIndexDTO.getGroupId()));
    }
}
