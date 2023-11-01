package fr.bankwiz.server.infrastructure.spi;

import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.spi.GroupRightSpi;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;

@Component
public class GroupRightSpiImpl implements GroupRightSpi {

    @Override
    public GroupRight save(GroupRight groupRight) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
    
}
