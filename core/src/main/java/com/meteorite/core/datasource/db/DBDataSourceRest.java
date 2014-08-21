package com.meteorite.core.datasource.db;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * DBDataSource Rest
 *
 * @author wei_jc
 * @since 1.0.0
 */
@Path("/db")
public class DBDataSourceRest {

    @GET
    @Path("/datasource")
    @Produces(MediaType.TEXT_PLAIN)
    public String getDataSource() {
        return "DataSource:";
    }
}
