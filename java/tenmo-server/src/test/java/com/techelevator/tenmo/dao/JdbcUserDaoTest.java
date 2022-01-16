package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.junit.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcUserDaoTest {
    public static SingleConnectionDataSource ds;
    private JdbcUserDao dao;
    private JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void beforeEverything() {
        ds = new SingleConnectionDataSource();
        ds.setUrl("jdbc:postgresql://localhost:5432/tenmo");
        ds.setUsername("postgres");
        ds.setPassword("postgres1");
        ds.setAutoCommit(false);
    }
    @AfterClass
    public static void afterEverything() {
        ds.destroy();
    }
    @Before
    public void beforeEach() {
        this.jdbcTemplate = new JdbcTemplate(this.ds);
        this.dao = new JdbcUserDao(jdbcTemplate);
        loadData();
    }
    @After
    public void afterEach() throws SQLException {
        ds.getConnection().rollback();
    }

    public void loadData() {
        String sql1 = "INSERT INTO users(user_id, username, password_hash) VALUES (8001, 'Melissa', 'aaa')";
        jdbcTemplate.update(sql1);
        String sql2 = "INSERT INTO accounts(account_id, user_id, balance) VALUES (9001, 8001, 1000)";
        jdbcTemplate.update(sql2);
        String sql3 = "INSERT INTO users(user_id, username, password_hash) VALUES (8002, 'Anthony', 'bbb')";
        jdbcTemplate.update(sql3);
        String sql4 = "INSERT INTO accounts(account_id, user_id, balance) VALUES (9002, 8002, 1000)";
        jdbcTemplate.update(sql4);
        String sql5 = "INSERT INTO transfers(transfer_id, transfer_type_id,transfer_status_id,account_from,account_to,amount) VALUES(999,2,2,9001,9002,500)";
        jdbcTemplate.update(sql5);

    }

    @Test
    public void getBalance() {
        dao.setBalance(new BigDecimal("1000"));
        BigDecimal actual = dao.getBalance();
        BigDecimal expected = new BigDecimal("1000");
        assertEquals(actual, expected);
    }

    @Test
    public void setBalance() {
        dao.setBalance(new BigDecimal("2"));
        BigDecimal actual = dao.getBalance();
        BigDecimal expected = new BigDecimal("2");
        assertEquals(actual, expected);

    }


    public BigDecimal checkBalance(int userId){
        String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        BigDecimal balance = new BigDecimal("0");
        if (result.next()){
           balance = result.getBigDecimal("balance");
        }
        return balance;
    }

    @Test
    public void updateBalance() {
        dao.updateBalance(new BigDecimal("100"), 8001);
        BigDecimal currBalance = checkBalance(8001);
        assertEquals(new BigDecimal("100.00"), currBalance);
        assertNotEquals(new BigDecimal("10"), currBalance);
    }

    @Test
    public void findIdByUsername() {
        int actual = dao.findIdByUsername("Anthony");
        assertEquals(actual, 8002);

        int actual2 = dao.findIdByUsername("Melissa");
        assertEquals(actual2, 8001);

    }


    @Test
    public void findAll() {

        List<User> actualUsers = dao.findAll();
        assertNotNull(actualUsers);

    }

    @Test
    public void findByUsername() {

        User userActual = dao.findByUsername("Anthony");
        Long userActualId = userActual.getId();
        Long expectedId = 8002L;
        assertEquals(userActualId, expectedId);
        String userName = userActual.getUsername();
        assertEquals(userName, "Anthony");
    }


    public Long checkIfAccountAdded(Long user_id){
        String sql = "SELECT user_id FROM accounts WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, user_id);
        Long userId = null;
        if (result.next()){
            userId = result.getLong("user_id");
        }

        return userId;
    }

    @Test
    public void create() {
        boolean actualCreate = dao.create("Joe", "123");
        assertEquals(true,actualCreate);

        User actualUser = dao.findByUsername("joe");
        actualUser.getId();
        assertNotNull(actualUser);

        Long actualId = checkIfAccountAdded(actualUser.getId());
        assertEquals(actualId, actualUser.getId());

    }
}