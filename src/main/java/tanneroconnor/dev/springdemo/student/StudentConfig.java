package tanneroconnor.dev.springdemo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student jess = new Student(
                    "Jess",
                    "Swanson",
                    LocalDate.of(1990, Month.APRIL, 1),
                    "jess@gmail.com"
            );

            Student billy = new Student(
                    "Billy",
                    "Bobb",
                    LocalDate.of(2001, Month.DECEMBER, 12),
                    "billybob32@gmail.com"
            );

            studentRepository.saveAll(
                    List.of(jess, billy)
            );
        };
    }
}
