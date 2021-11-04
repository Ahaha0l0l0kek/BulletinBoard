package eu.senla.alexbych.bulletinboard.backend.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("eu.senla.alexbych.bulletinboard.backend.repository")
@EntityScan({"eu.senla.alexbych.bulletinboard.backend.model", "eu.senla.alexbych.bulletinboard.chat.model"})
public class JpaConfig {
}
