package io.github.willdomkahari.loanmanagement;

import io.github.willdomkahari.usermanagement.annotation.DomainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan(
        basePackages = "io.github.willdomkahari",
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = DomainService.class
        )
)
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class LoanManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanManagementApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runner(UserService service){
//        return args -> {
//           var user = service.findUserByUsername("wadu");
//            System.out.println("user = " + user);
//        };
//    }

}
