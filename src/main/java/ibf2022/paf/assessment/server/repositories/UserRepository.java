package ibf2022.paf.assessment.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.User;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jt;

    public Optional<User> findUserByUsername(String username) {
        String sql = "select * from user where name = ?";
        try {
            User user = jt.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class), username);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public String insertUser(User user) {
        String sql = """
            insert into user (user_id, username, name) values (?,?,?)
                """;
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        jt.update(sql, uuid, user.getUsername(), user.getName()); // throws DataAccessException
        return uuid;
    }
}
