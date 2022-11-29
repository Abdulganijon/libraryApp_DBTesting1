package com.cydeo.steps;

import com.cydeo.utility.ConfigurationReader;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class US1_Step_Definitions {
    ResultSetMetaData rsmd;
    ResultSetMetaData rsmdUnque;
    int columnCount;
    int columnCountUnique;
    List<String> allColumns;

    @Given("Establish the database connection")
    public void establish_the_database_connection() throws SQLException {
        String url = ConfigurationReader.getProperty("library2.db.url");
        String dbUsername = ConfigurationReader.getProperty("library2.db.username");
        String dbPassword = ConfigurationReader.getProperty("library2.db.password");

        DB_Util.createConnection(url, dbUsername, dbPassword);

    }

    @When("Execute query to get all IDs from users")
    public void execute_query_to_get_all_i_ds_from_users() throws SQLException {
        ResultSet rs1 = DB_Util.runQuery("select id from users");
        rsmd = rs1.getMetaData();
        columnCount = DB_Util.getRowCount();
        System.out.println(columnCount);
    }

    @Then("verify all users has unique ID")
    public void verify_all_users_has_unique_id() throws SQLException {
        ResultSet rsUnique = DB_Util.runQuery("select distinct id from users");
        rsmdUnque = rsUnique.getMetaData();
        columnCountUnique = DB_Util.getRowCount();
        Assert.assertEquals(columnCount, columnCountUnique);
    }

        @When("Execute query to get all columns")
        public void execute_query_to_get_all_columns () throws SQLException {
        ResultSet rsAllColumns = DB_Util.runQuery("select * from users");
        rsmd= rsAllColumns.getMetaData();
        allColumns = DB_Util.getAllColumnNamesAsList();
            System.out.println(allColumns);

        }
        @Then("verify the below columns are listed in result")
        public void verify_the_below_columns_are_listed_in_result (List<String> expectedColumnName){
        Assert.assertEquals(allColumns,expectedColumnName);

        }
    }

