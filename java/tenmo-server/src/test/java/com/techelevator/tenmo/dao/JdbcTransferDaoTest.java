package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcTransferDaoTest{
    public static SingleConnectionDataSource ds;
    private TransferDao dao;
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
        this.dao = new JdbcTransferDao(ds);
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
    public void getAccountIdFromUserId() {

        int testAccountId = dao.getAccountIdFromUserId(8001);
        assertEquals(testAccountId, 9001);
        int testAccountId2 = dao.getAccountIdFromUserId(8002);
        assertEquals(testAccountId2, 9002);

    }

    @Test
    public void getAccountIdFromUserName() {
        int testAccountId = dao.getAccountIdFromUserName("Melissa");
        assertEquals(testAccountId, 9001);
        int testAccountId2 = dao.getAccountIdFromUserName("Anthony");
        assertEquals(testAccountId2, 9002);
    }

    @Test
    public void getNameFromAccountId() {
        String testName = dao.getNameFromAccountId(9001);
        assertEquals(testName, "Melissa");
        String testName2 = dao.getNameFromAccountId(9002);
        assertEquals(testName2, "Anthony");

    }

    public int testCreateMethod(int accountId){
        String sql = "SELECT transfer_id FROM transfers WHERE transfer_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        BigDecimal amount = new BigDecimal("0");
        int id = 0;
        if(result.next()){
            //System.out.println(result.getInt("account_from"));
            id = result.getInt("transfer_id");
            System.out.println(id);

        }

        return id;
    }

    @Test
    public void create() {
        Transfer transferTest = new Transfer(999,2,8001,8002, new BigDecimal("100"),"","");
        dao.create(transferTest);
        assertEquals(testCreateMethod(999), 999);

    }

    @Test
    public void findTransferHistory() {
        List<Transfer> transfers = dao.findTransferHistory("Melissa");
        assertNotNull(transfers);
        assertEquals(transfers.size(), 1);

    }

    @Test
    public void updateTransferStatus() {



    }


}