package com.randreucetti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

public class App {

	public final static Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws InterruptedException {
		Cluster cluster = Cluster.builder()
				.addContactPoints("127.0.0.1", "127.0.0.2", "127.0.0.3")
				.build();
		Session session = cluster.connect("test");
		while (true) {
			Thread.sleep(5000);
			ResultSet rs = session
					.execute("SELECT cql_version FROM system.local;");
			log.info(rs.toString());
		}
	}
}
