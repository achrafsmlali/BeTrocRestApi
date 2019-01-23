package com.betroc;

import com.betroc.config.ImageStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        BeTrocRestApiApplication.class,
        Jsr310JpaConverters.class
})
@EnableConfigurationProperties({
        ImageStorageProperties.class
})
public class BeTrocRestApiApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

	public static void main(String[] args) {
		SpringApplication.run(BeTrocRestApiApplication.class, args);
	}

}
