package com.randreucetti;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class AppTest {
	public static void main(String[] args) {
		Cluster cluster = Cluster.builder()
				.addContactPoints("127.0.0.1", "127.0.0.2", "127.0.0.3")
				.build();
		Session session = cluster.connect("test");
	}
}
