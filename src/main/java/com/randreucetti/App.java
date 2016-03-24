package com.randreucetti;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
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
		InetSocketAddress n1 = new InetSocketAddress("192.168.1.184", 15001);
		InetSocketAddress n2 = new InetSocketAddress("192.168.1.184", 15002);
		InetSocketAddress n3 = new InetSocketAddress("192.168.1.184", 15003);
		InetSocketAddress n4 = new InetSocketAddress("192.168.1.184", 15004);
		InetSocketAddress n5 = new InetSocketAddress("192.168.1.184", 15005);
		List<InetSocketAddress> addresses = Arrays.asList(n1, n2, n3, n4, n5);
		Cluster cluster = Cluster.builder()
				.addContactPointsWithPorts(addresses).build();
		Session session = cluster.connect("test");

		PreparedStatement statement = session
				.prepare("INSERT INTO emp (emp_id, emp_city, emp_name, emp_phone, emp_sal VALUES (?, ?, ?, ?, ?)");
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
