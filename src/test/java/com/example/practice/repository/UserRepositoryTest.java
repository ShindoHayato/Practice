package com.example.practice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.SqlMergeMode.MergeMode.MERGE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.util.ReflectionTestUtils;

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
        assertEquals(6, count);
    }
}
