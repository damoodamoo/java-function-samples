package com.damoo.samples.helpers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DSInstance {

    private static HikariDataSource ds = null;
    private static final Object lock = new Object();  

    private DSInstance(){}
  
    public static HikariDataSource GetDS(){
        if(ds == null){
            synchronized (lock){               
                if(ds == null){         
                    String jdbcUrl = System.getenv("JDBCUrl");
                    String dbUsername = System.getenv("DBUsername");
                    String dbPassword = System.getenv("DBPassword");
                    HikariConfig config = new HikariConfig();
                    config.setJdbcUrl(jdbcUrl);
                    config.setUsername(dbUsername);
                    config.setPassword(dbPassword);
                    config.addDataSourceProperty("cachePrepStmts", "true");
                    config.addDataSourceProperty("prepStmtCacheSize", "250");
                    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
                    ds = new HikariDataSource(config);
                }
            }
        }        

        return ds;
    }
    

}