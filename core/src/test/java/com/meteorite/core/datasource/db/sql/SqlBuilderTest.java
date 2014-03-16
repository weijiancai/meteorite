package com.meteorite.core.datasource.db.sql;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class SqlBuilderTest {
    private SqlBuilder sql;

    @Before
    public void init() {
        sql = SqlBuilder.getInstance();
    }

    @After
    public void destroy() {
        sql = null;
    }

    @Test
    public void testQuery() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person";
        String actual = sql.from("person").query("personId").query("name").query("age, sex").build();

        assertThat(actual, equalTo(expect));
    }

    @Test
    public void testFrom() throws Exception {
        String expect = "SELECT * FROM person";

        assertThat(sql.from("person").build(), equalTo(expect));
    }

    @Test
    public void testJoin() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .build();

        assertThat(actual, equalTo(expect));

        expect = "SELECT personId, name, age, sex FROM person p JOIN bu b ON (p.personId = b.personId)";
        actual = SqlBuilder.getInstance().from("person p")
                .join("bu b ON (p.personId = b.personId)")
                .query("personId").query("name").query("age, sex")
                .build();

        assertThat(actual, equalTo(expect));
    }

    @Test
    public void testWhere() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T'";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .build();

        assertThat(actual, equalTo(expect));
    }

    @Test
    public void testAnd() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' AND personId = 5";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .and("personId = 5")
                .build();

        assertThat(actual, equalTo(expect));

        expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' AND personId = ? AND name = ?";
        sql = SqlBuilder.getInstance();
        actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .and("personId = ", 5)
                .and("name = " , "zhangSan")
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{5, "zhangSan"}));
    }

    @Test
    public void testAndBracket() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' AND personId = 5 AND (sex = ?";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .and("personId = 5")
                .andBracket("sex = ", "F")
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{"F"}));
    }

    @Test
    public void testAndBracketLike() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' AND personId = 5 AND (sex LIKE ?";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .and("personId = 5")
                .andBracketLike("sex", "%%%s%%", "F")
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{"%F%"}));
    }

    @Test
    public void testBracket() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' AND personId = 5 AND (sex LIKE ?)";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .and("personId = 5")
                .andBracketLike("sex", "%%%s%%", "F")
                .rightBracket()
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{"%F%"}));

        sql = SqlBuilder.getInstance();
        expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' AND (personId=? OR personId=?)";
        actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .andBracket("personId=", 5)
                .or("personId=", 6)
                .rightBracket()
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{5, 6}));

        sql = SqlBuilder.getInstance();
        expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T'";
        actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .andBracket("personId=", "")
                .or("personId=", "")
                .rightBracket()
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{}));

        sql = SqlBuilder.getInstance();
        expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T'";
        actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .andBracket()
                .and("personId=", "")
                .or("personId=", "")
                .rightBracket()
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{}));
    }

    @Test
    public void testAndIn() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' AND personId = 5 AND (sex LIKE ?) AND age IN (23,25,24)";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .and("personId = 5")
                .andBracketLike("sex", "%%%s%%", "F")
                .rightBracket()
                .andIn("age", "23,25,24")
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{"%F%"}));
    }

    @Test
    public void testOrIn() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' AND personId = 5 AND (sex LIKE ?) OR age IN (23,24,25)";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .and("personId = 5")
                .andBracketLike("sex", "%%%s%%", "F")
                .rightBracket()
                .orIn("age", "23,24,25")
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{"%F%"}));
    }

    @Test
    public void testAndLike() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' AND personId = 5 AND sex LIKE ?";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .and("personId = 5")
                .andLike("sex", "%%%s%%", "F")
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{"%F%"}));
    }

    @Test
    public void testOrLike() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' AND personId = 5 OR sex LIKE ?";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .and("personId = 5")
                .orLike("sex", "%%%s%%", "F")
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{"%F%"}));
    }

    @Test
    public void testAndDate() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' AND personId = 5 AND sex LIKE ? AND createTime = to_date(?, 'yyyy-MM-dd')";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .and("personId = 5")
                .andLike("sex", "%%%s%%", "F")
                .andDate("createTime =", "2011-02-02")
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{"%F%", "2011-02-02"}));
    }

    @Test
    public void testOr() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' OR personId = 5";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .or("personId = 5")
                .build();

        assertThat(actual, equalTo(expect));

        expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' AND personId = ? OR name = ?";
        sql = SqlBuilder.getInstance();
        actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .and("personId = ", 5)
                .or("name = ", "zhangSan")
                .build();

        assertThat(actual, equalTo(expect));
        assertThat(sql.getParamsValue(), equalTo(new Object[]{5, "zhangSan"}));
    }

    @Test
    public void testDesc() throws Exception {
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' OR personId = 5 ORDER BY name DESC";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .or("personId = 5")
                .desc("name")
                .build();

        assertThat(actual, equalTo(expect));
    }

    @Test
    public void testWith() throws Exception {
        String with = "with aaa SELECT name FROM person";
        String expect = with + " SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' OR personId = 5 ORDER BY name DESC";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .or("personId = 5")
                .desc("name")
                .with(with)
                .build();

        assertThat(actual, equalTo(expect));
    }

    @Test
    public void testGetCountSql() throws Exception {
        String with = "with aaa SELECT name FROM person";
        String expect = with + " SELECT count(1) FROM person JOIN bu WHERE enable='T' OR personId = 5 ORDER BY name DESC";
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .or("personId = 5")
                .desc("name")
                .with(with)
                .getCountSql();

        assertThat(actual, equalTo(expect));
    }

    @Test
    public void testGetPageSql() throws Exception {
        int start = 0;
        int end = 10;
        String expect = "SELECT personId, name, age, sex FROM person JOIN bu WHERE enable='T' OR personId = 5 ORDER BY name DESC";
        String page = String.format("SELECT * FROM (SELECT nowpage.*,rownum rn FROM (%s) nowpage WHERE rownum<=%d) WHERE rn>%d", expect, end, start);
        String actual = sql.from("person")
                .join("bu")
                .query("personId").query("name").query("age, sex")
                .where("enable='T'")
                .or("personId = 5")
                .desc("name")
                .getPageSql(start, end);

        assertThat(actual, equalTo(page));
    }

    @Test
    public void testGetParamsValue() {
        Object[] expect = {123, "zhangSan", 23, "F"};
        sql.from("person")
                .where("enable='T'")
                .and("personId=", 123)
                .and("name=", "zhangSan")
                .and("age=", 23)
                .and("sex=", "F")
        ;

        assertThat(sql.getParamsValue(), equalTo(expect));
    }

    @Test
    public void testMax() {
        String expect = "SELECT MAX(db_version) AS max_db_version FROM sys_db_version WHERE sys_name='core' GROUP BY sys_name";
        String actual = SqlBuilder.getInstance()
                .from("sys_db_version")
                .max("db_version")
                .where("sys_name='core'")
                .group("sys_name")
                .build();

        assertThat(actual, equalTo(expect));
    }

    @Test
    public void testProperties() {
        System.getProperties().list(System.out);
    }
}
