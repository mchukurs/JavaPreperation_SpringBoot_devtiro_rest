package com.chukurs.database;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

//@SpringBootApplication
@Log
public class DatabaseApplicationOLD implements CommandLineRunner {
    //object to interact with database
    private final DataSource dataSource;

    public DatabaseApplicationOLD(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplicationOLD.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Datasource: " + dataSource.toString());
        final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
        restTemplate.execute("SELECT 1");

    }
}
