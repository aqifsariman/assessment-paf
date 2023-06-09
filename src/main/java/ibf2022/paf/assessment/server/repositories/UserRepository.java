package ibf2022.paf.assessment.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.User;

// TODO: Task 3
@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String FIND_USER_SQL = "select * from user where username = ?";
    private final String CREATE_USER_SQL = "insert into user (user_id, username, name) values (?, ?, ?)";

    public Optional<User> findUserByUsername(String username) {
        try {
            User user = jdbcTemplate.queryForObject(FIND_USER_SQL, BeanPropertyRowMapper.newInstance(User.class),
                    username);
            return Optional.of(user);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("Exception: " + e);
            return Optional.empty();

        }
    }

    public String insertUser(User user) {
        // Generate user_id
        String randomHex = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        user.setUserId(randomHex);
        Integer insertUser = jdbcTemplate.update(CREATE_USER_SQL,
                user.getUserId(),
                user.getUsername(),
                user.getName());
        return (insertUser > 0) ? user.getUserId() : "Unsuccessful.";
    }
}
