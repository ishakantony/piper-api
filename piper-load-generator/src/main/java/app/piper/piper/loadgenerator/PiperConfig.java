package app.piper.piper.loadgenerator;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class PiperConfig {

    @Bean
    public Faker faker() {
        return new Faker();
    }

}
