package fr.bankwiz.server.infrastructure.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.bankwiz.server.domain.api.BankAccountApi;
import fr.bankwiz.server.domain.api.GroupApi;
import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.service.BankAccountService;
import fr.bankwiz.server.domain.service.GroupDomainService;
import fr.bankwiz.server.domain.service.UserDomainService;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.domain.spi.BankAccountSpi;
import fr.bankwiz.server.domain.spi.GroupRightSpi;
import fr.bankwiz.server.domain.spi.GroupSpi;
import fr.bankwiz.server.domain.spi.UserSpi;
import fr.bankwiz.server.domain.tools.CheckRightTools;

@Configuration
public class DomainConfiguration {

    @Bean
    UserApi userApi(AuthenticationSpi authenticationSpi, UserSpi userSpi) {
        return new UserDomainService(authenticationSpi, userSpi);
    }

    @Bean
    CheckRightTools checkRightTools(GroupRightSpi groupRightSpi) {
        return new CheckRightTools(groupRightSpi);
    }

    @Bean
    GroupApi groupApi(
            GroupSpi groupSpi,
            GroupRightSpi groupRightSpi,
            UserSpi userSpi,
            AuthenticationSpi authenticationSpi,
            CheckRightTools checkRightTools) {
        return new GroupDomainService(groupSpi, groupRightSpi, userSpi, authenticationSpi, checkRightTools);
    }

    @Bean
    BankAccountApi bankAccountApi(
            BankAccountSpi bankAccountSpi,
            GroupSpi groupSpi,
            AuthenticationSpi authenticationSpi,
            CheckRightTools checkRightTools) {
        return new BankAccountService(bankAccountSpi, groupSpi, authenticationSpi, checkRightTools);
    }
}
