package com.kitsuneindustries.deathwatch2.store;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;

import com.kitsuneindustries.deathwatch2.Deathwatch2;

import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class DeathStore {
	
	private static final Logger log = Deathwatch2.getLogger();
	
	private static DeathStore instance;
	
	public static DeathStore getInstance() {
		if (instance == null) {
			instance = new DeathStore();
		}
		return instance;
	}
	
	private String getResourceAsString(String resource) {
		try (InputStream iStream = this.getClass().getClassLoader().getResourceAsStream(resource)) {
			return IOUtils.toString(iStream, Charset.forName("UTF-8"));
		} catch (IOException e) {
			log.error("Error loading resource " + resource, e);
			throw new RuntimeException(e);
		}
		
	}
	private Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:h2:mem:deathstore");
		String sql = getResourceAsString("sql/schema.sql");
		conn.prepareStatement(sql).execute();
		log.info("Connected to database");
		return conn;
	}
	
	public void persistDeath( LivingDeathEvent event ) {
		try {
			Connection conn = getConnection();
			// TODO Persist the death
		} catch (SQLException e) {
			log.error("Error persisting death to the database", e);
		}
	}

}
