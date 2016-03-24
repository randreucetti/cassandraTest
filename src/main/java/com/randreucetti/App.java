package com.randreucetti;

import java.math.BigInteger;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

public class App {

	public final static Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws InterruptedException,
			UnknownHostException {
		Cluster cluster = Cluster
				.builder()
				.addContactPoints("127.0.0.1", "127.0.0.2", "127.0.0.3",
						"127.0.0.4", "127.0.0.5").build();
		Session session = cluster.connect("test");

		PreparedStatement statement = session
				.prepare("INSERT INTO emp (emp_id, emp_city, emp_name, emp_phone, emp_sal) VALUES (?, ?, ?, ?, ?)");
		Random r = new Random();
		SessionIdentifierGenerator stringGenerator = new SessionIdentifierGenerator();
		while (true) {
			Thread.sleep(5000);
			ResultSet rs = session.execute(statement.bind(r.nextInt(1000000),
					stringGenerator.nextSessionId(),
					stringGenerator.nextSessionId(), r.nextInt(1000000),
					r.nextInt(1000000)));
			log.info(rs.toString());
		}
	}
}

final class SessionIdentifierGenerator {
	private SecureRandom random = new SecureRandom();

	public String nextSessionId() {
		return new BigInteger(130, random).toString(10);
	}
}
