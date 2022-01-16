package com.techelevator.tenmo.dao;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.sql.SQLException;
import static org.junit.Assert.*;

public class JdbcAccountDaoTest {
    public static SingleConnectionDataSource ds;
    private AccountDao dao;
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
        this.dao = new JdbcAccountDao(jdbcTemplate);
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
    }
    @Test
    public void retrieveBalance() {
        BigDecimal actualResult = dao.retrieveBalance(8001);
        BigDecimal expectedResult = new BigDecimal("1000.00");
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void updateBalance(){
        dao.updateBalance(new BigDecimal("2.00"), 8001);
        assertEquals(dao.retrieveBalance(8001), new BigDecimal("2.00"));
    }



}