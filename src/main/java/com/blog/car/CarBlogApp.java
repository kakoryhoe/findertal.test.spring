package com.blog.car;

import com.blog.car.config.ApplicationProperties;
import com.blog.car.domain.Authority;
import com.blog.car.domain.Car;
import com.blog.car.domain.Comment;
import com.blog.car.domain.User;
import com.blog.car.repository.AuthorityRepository;
import com.blog.car.repository.CarRepository;
import com.blog.car.repository.CommentRepository;
import com.blog.car.repository.UserRepository;
import com.blog.car.security.AuthoritiesConstants;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.jhipster.config.DefaultProfileUtil;
import tech.jhipster.config.JHipsterConstants;

@SpringBootApplication
@EnableConfigurationProperties({ LiquibaseProperties.class, ApplicationProperties.class })
public class CarBlogApp {

    private static final Logger log = LoggerFactory.getLogger(CarBlogApp.class);

    private final Environment env;

    public CarBlogApp(Environment env) {
        this.env = env;
    }

    /**
     * Initializes carBlog.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)
        ) {
            log.error(
                "You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time."
            );
        }
        if (
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)
        ) {
            log.error(
                "You have misconfigured your application! It should not " + "run with both the 'dev' and 'cloud' profiles at the same time."
            );
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CarBlogApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    @Bean
    public static CommandLineRunner populateDB(
        CarRepository carRepository,
        CommentRepository commentRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository
    ) {
        return args -> {
            User admin = new User();
            admin.setLogin("admin");
            admin.setActivated(true);
            admin.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(admin);

            User author01 = new User();
            author01.setLogin("user01");
            author01.setPassword(passwordEncoder.encode("pwd123"));
            author01.setActivated(true);
            HashSet<Authority> author01Authorities = new HashSet<>();
            authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(author01Authorities::add);
            author01.setAuthorities(author01Authorities);
            author01 = userRepository.save(author01);
            Comment comment01 = new Comment("Nice car!!", author01);
            comment01 = commentRepository.save(comment01);
            Comment comment02 = new Comment("Best sport car ever!", author01);
            comment02 = commentRepository.save(comment02);
            List<Comment> car01Comments = new ArrayList<>();
            car01Comments.add(comment01);
            car01Comments.add(comment02);

            User author02 = new User();
            author02.setLogin("user02");
            author02.setPassword(passwordEncoder.encode("pwd1234"));
            author02.setActivated(true);
            author02.setAuthorities(author01Authorities);
            author02 = userRepository.save(author02);
            Comment comment03 = new Comment("I prefer the previous model.", author02);
            comment03 = commentRepository.save(comment03);
            List<Comment> car03Comments = new ArrayList<>();
            car03Comments.add(comment03);

            Car car01 = new Car("Ford", "Mustang", 39000.0);
            Car car02 = new Car("Nissan", "Kicks", 21000.0);
            Car car03 = new Car("BMW", "X7", 35000.0);
            Car car04 = new Car("Range Rover", "Evoque", 43300.0);

            car01 = carRepository.save(car01);
            car02 = carRepository.save(car02);
            car03 = carRepository.save(car03);
            car04 = carRepository.save(car04);

            car01.setComments(car01Comments);
            carRepository.save(car01);
            car03.setComments(car03Comments);
            carRepository.save(car03);
        };
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional
            .ofNullable(env.getProperty("server.servlet.context-path"))
            .filter(StringUtils::isNotBlank)
            .orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
            "\n----------------------------------------------------------\n\t" +
            "Application '{}' is running! Access URLs:\n\t" +
            "Local: \t\t{}://localhost:{}{}\n\t" +
            "External: \t{}://{}:{}{}\n\t" +
            "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles()
        );
    }
}
