package fr.bankwiz.server.infrastructure.unittest.transformer.grouptransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToGroupIndexDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final GroupDomain group = this.factory.getGroup();
        final GroupIndexDTO groupIndexDTO = GroupTransformer.toGroupIndexDTO(group);
        Assertions.assertAll(
                () -> Assertions.assertEquals(group.getGroupName(), groupIndexDTO.getGroupName()),
                () -> Assertions.assertEquals(group.getId(), groupIndexDTO.getGroupId()));
    }
}
