package azmiu.library;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import static org.springframework.boot.SpringApplication.run;
@ConfigurationPropertiesScan
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        run(Application.class, args);
    }
}