package org.igot.utils;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CassandraConnection {

    public static void createConnection(){
        Cluster cluster = null;
        try {
            cluster = Cluster.builder()                                                    // (1)
                    .addContactPoint("127.0.0.1")
                    .build();
            Session session = cluster.connect();                                           // (2)

            ResultSet rs = session.execute("select * from sunbird.org_hierarchy limit 1");    // (3)
            Row row = rs.one();
            System.out.println(row.getString("orgname"));                          // (4)
        } finally {
            if (cluster != null) cluster.close();                                          // (5)
        }

    }
}
