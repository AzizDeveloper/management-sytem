package dev.aziz;

import dev.aziz.config.security.PasswordConfig;
import dev.aziz.entities.Comment;
import dev.aziz.entities.Priority;
import dev.aziz.entities.Role;
import dev.aziz.entities.Status;
import dev.aziz.entities.Task;
import dev.aziz.entities.User;
import dev.aziz.repositories.RoleRepository;
import dev.aziz.repositories.TaskRepository;
import dev.aziz.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableAsync
public class TaskManagementApplication {

    //TODO: write tests for main parts
    //TODO: add commenting explicitly
    //todo: use DTO in controllers not JPA entities
    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository userRepository,
            RoleRepository roleRepository,
            TaskRepository taskRepository,
            PasswordConfig passwordConfig) {
        return args -> {

            Role authorRole = new Role("AUTHOR");
            Role performerRole = new Role("PERFORMER");

            roleRepository.save(authorRole);
            roleRepository.save(performerRole);

            Comment commentTask1 = Comment.builder()
                    .body("Create docker compose file and configure and start working with database.")
                    .build();

            Comment commentTask2 = Comment.builder()
                    .body("Add all test dependencies and use Mockito and so.")
                    .build();

            Comment commentTask3 = Comment.builder()
                    .body("Create all layers models, services, controllers.")
                    .build();

            Task task1 = Task.builder()
                    .title("Connect to database")
                    .status(Status.PROCESSING)
                    .priority(Priority.HIGH)
                    .comments(List.of(commentTask1))
                    .build();
            taskRepository.save(task1);
            Task task2 = Task.builder()
                    .title("Write Unit tests")
                    .status(Status.WAITING)
                    .priority(Priority.MEDIUM)
                    .comments(List.of(commentTask2))
                    .build();
            taskRepository.save(task2);

            Task task3 = Task.builder()
                    .title("Create REST API starting code")
                    .status(Status.DONE)
                    .priority(Priority.LOW)
                    .comments(List.of(commentTask3))
                    .build();
            taskRepository.save(task3);


            User aziz = new User(
                    "aziz",
                    "abdukarimov",
                    "azizdev",
                    passwordConfig.passwordEncoder().encode("asdasd"),
                    true,
                    Set.of(authorRole),
                    List.of(task1, task2, task3)
            );

            userRepository.save(aziz);

            User bob = new User(
                    "bob",
                    "john",
                    "bobdev",
                    passwordConfig.passwordEncoder().encode("asdasd"),
                    true,
                    Set.of(performerRole),
                    List.of(task1)
            );
            userRepository.save(bob);

            User azim = new User(
                    "Azim",
                    "Abdukarimov",
                    "azimdev",
                    passwordConfig.passwordEncoder().encode("asdasd"),
                    true,
                    Set.of(performerRole),
                    List.of(task2, task3)
            );
            userRepository.save(azim);

            User sergio = new User(
                    "sergio",
                    "lema",
                    "sergiodev",
                    passwordConfig.passwordEncoder().encode("asdasd"),
                    true,
                    Set.of(performerRole)
            );
            userRepository.save(sergio);
        };
    }

}
