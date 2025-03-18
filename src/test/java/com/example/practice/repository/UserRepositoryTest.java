package com.example.practice.repository;

import static org.springframework.test.context.jdbc.SqlMergeMode.MergeMode.MERGE;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.practice.dto.User;
import com.example.practice.exception.Error;
import com.example.practice.exception.ApplicationException;
import com.example.practice.repository.mapper.UserMapper;

@SpringBootTest
@Sql(value = "/sql/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SqlMergeMode(MERGE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    private final UserMapper mockMapper = Mockito.mock(UserMapper.class);

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(userRepository, "userMapper", userMapper);
    }

    /*
     * count
     */
    @Test
    @Sql(value = "/sql/user/insert_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void countNormalTest() {
        int count = userRepository.count();
        Assertions.assertEquals(6, count);
    }

    @Test
    void countSystemErrorTest() {
        Mockito.when(mockMapper.count()).thenThrow(DataRetrievalFailureException.class);
        ReflectionTestUtils.setField(userRepository, "userMapper", mockMapper);
        ApplicationException e = Assertions.assertThrows(ApplicationException.class, () -> userRepository.count());
        Assertions.assertEquals(Error.DB_ERROR, e.getError());
        Mockito.verify(mockMapper, Mockito.times(1)).count();
    }

    /*
     * findById
     */
    @Test
    @Sql(value = "/sql/user/insert_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByIdNormalTest() {
        User user = userRepository.findById(1);
        User expected = new User(
                1,
                "tester1@test.jp",
                "$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y",
                "ROLE_USER",
                LocalDateTime.of(2022, 1, 1, 0 ,0, 0),
                LocalDateTime.of(2022, 1, 1, 0 ,0, 0),
                true);
        Assertions.assertEquals(expected, user);
    }

    /*
     * findAll
     */
    @Test
    @Sql(value = "/sql/user/insert_user_2.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAllNormalTest() {
        List<User> user = userRepository.findAll();
        List<User> expected = List.of(
                new User(
                        1,
                        "tester3@test.jp",
                        "$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y",
                        "ROLE_ADMIN",
                        LocalDateTime.of(2022, 1, 1, 0 ,0, 0),
                        LocalDateTime.of(2022, 1, 1, 0 ,0, 0),
                        true),
                new User(
                        2,
                        "tester4@test.jp",
                        "$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y",
                        "ROLE_ADMIN",
                        LocalDateTime.of(2022, 1, 1, 0 ,0, 0),
                        LocalDateTime.of(2022, 1, 1, 0 ,0, 0),
                        true),
                new User(
                        3,
                        "tester5@test.jp",
                        "$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y",
                        "ROLE_ADMIN",
                        LocalDateTime.of(2022, 1, 1, 0 ,0, 0),
                        LocalDateTime.of(2022, 1, 1, 0 ,0, 0),
                        true));
        Assertions.assertEquals(expected, user);
    }

    /*
     * insert
     */
    @Test
    void createNormal2RoleTest() {
        Assertions.assertDoesNotThrow(() -> userRepository.insert("create_user1@test.jp", "password", LocalDateTime.now(), "USER"));

        List<User> users = userRepository.findAll();
        User user = users.get(users.size() - 1);
        Assertions.assertEquals("create_user1@test.jp", user.getMail());
        Assertions.assertEquals("password", user.getPassword());
        Assertions.assertEquals("USER", user.getRoles());
        Assertions.assertNotNull(user.getCreated());
        Assertions.assertNotNull(user.getLast_logined());
        Assertions.assertEquals(true, user.isEnabled());
    }

    /*
     * updateLastLogined
     */
    @Test
    @Sql(value = "/sql/user/insert_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updateLastLoginedNoIdTest() {
        Assertions.assertDoesNotThrow(() -> userRepository.updateLastLogined(
            1,
            "TESTER1@test.jp",
            "$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y",
            "ROLE_ADMIN",
            LocalDateTime.of(2023, 1, 1, 0 ,0, 0),
            true
        ));
        User user = userRepository.findById(1);
        User expected = new User(
                        1,
                        "TESTER1@test.jp",
                        "$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y",
                        "ROLE_ADMIN",
                        LocalDateTime.of(2022, 1, 1, 0 ,0, 0),
                        LocalDateTime.of(2023, 1, 1, 0 ,0, 0),
                        true
        );
        assertEquals(user, expected);
    }
}
