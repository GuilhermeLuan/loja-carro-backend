package dev.guilhermeluan.lojacarro.config;

import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@Import(TestcontainersConfiguration.class)
@ActiveProfiles("ittest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)

public class IntegrationTestConfig {
}
