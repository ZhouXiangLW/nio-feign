package niofeigncore;

import niofeigncore.annotations.EnableNioFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableNioFeignClients
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MainApplication.class);
        application.run(args);
    }
}
