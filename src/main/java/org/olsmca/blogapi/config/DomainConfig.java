package org.olsmca.blogapi.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("org.olsmca.blogapi.domain")
@EnableJpaRepositories("org.olsmca.blogapi.repos")
@EnableTransactionManagement
public class DomainConfig {
}
