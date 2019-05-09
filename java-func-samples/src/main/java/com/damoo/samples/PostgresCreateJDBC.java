package com.damoo.samples;

import java.util.*;

import com.microsoft.azure.functions.annotation.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.damoo.samples.helpers.DSInstance;
import com.microsoft.azure.functions.*;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Azure Functions with HTTP Trigger.
 */
public class PostgresCreateJDBC{

    @FunctionName("PostgresCreateJDBC")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");      

        try {
            Connection c = DSInstance.GetDS().getConnection();
   
            Statement stmt = c.createStatement();
            String sql = "INSERT INTO public.funcoutput (\"CorrelationId\", \"Body\") "
               + "VALUES ('12345667778', 'Coming from JDBC');";
            
            stmt.executeUpdate(sql);     
            stmt.close();
            c.close();
         } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
         }

        return request.createResponseBuilder(HttpStatus.OK).build();
    }
}
