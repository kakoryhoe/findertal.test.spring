package com.blog.car.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.blog.car.IntegrationTest;
import com.blog.car.domain.Authority;
import com.blog.car.domain.Car;
import com.blog.car.domain.Comment;
import com.blog.car.domain.User;
import com.blog.car.repository.AuthorityRepository;
import com.blog.car.repository.CarRepository;
import com.blog.car.repository.CommentRepository;
import com.blog.car.repository.UserRepository;
import com.blog.car.security.AuthoritiesConstants;
import com.blog.car.service.CarService;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@IntegrationTest
class CarResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private CarService carService;

    @Before
    public void initTest() {
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
    }

    @Test
    void getCars() throws Exception {
        int resultSize = carService.getCars().size();

        mockMvc
            .perform(get("/api/cars").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(resultSize)));
    }
}
