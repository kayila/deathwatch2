package com.kitsuneindustries.deathwatch2.store;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.google.common.annotations.VisibleForTesting;
import com.kitsuneindustries.deathwatch2.data.PlayerDeath;

import net.minecraftforge.common.DimensionManager;

public class PersistanceHelper {

	private static final Logger log = LogManager.getLogger(PersistanceHelper.class);
	private static SessionFactory sessionFactory;
	
	private static String getResourceAsString(String resource) {
		try (InputStream iStream = PersistanceHelper.class.getClassLoader().getResourceAsStream(resource)) {
			return IOUtils.toString(iStream, Charset.forName("UTF-8"));
		} catch (IOException e) {
			log.error("Error loading resource " + resource, e);
			throw new RuntimeException(e);
		}
	}
	public static void setup() {
		File rootFolder = DimensionManager.getCurrentSaveRootDirectory();
		assert rootFolder != null;
		setup("file:" + new File(rootFolder, "deathregistry").toString());
	}

	@VisibleForTesting
	static void setup(String databaseString) {
		log.warn("Creating new SessionFactory");
		try {
			SessionFactory sessionFactory = null;
			
			log.warn("Creating a new configuration");
			Configuration configuration = new Configuration();
			
			log.warn("Creating new properties");
			Properties prop = new Properties();
			prop.setProperty("hibernate.connection.url", "jdbc:h2:" + databaseString);
			prop.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
			
			log.warn("Setting properties");
			configuration.setProperties(prop);
			configuration.addAnnotatedClass(PlayerDeath.class);
			
			log.warn("Building service registry");
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			
			log.warn("Building session factory");
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			
			log.warn("Running create schema");
			try (Session session = sessionFactory.openSession()) {
				String sql = getResourceAsString("sql/schema.sql");
				session.beginTransaction();
				session.createSQLQuery(sql).executeUpdate();
				session.getTransaction().commit();
			}
			
			if (PersistanceHelper.sessionFactory == null) {
				log.warn("No previous sessionFactory existed");
				PersistanceHelper.sessionFactory = sessionFactory;
			} else {
				log.warn("Previous sessionfactory existed");
				// Store the old one and close it after the swap
				SessionFactory old = PersistanceHelper.sessionFactory;
				PersistanceHelper.sessionFactory = sessionFactory;
				old.close();
			}
		} catch (Exception e) {
			log.error("Error while setting up hibernate connection", e);
		}
	}

	public static Session getSession() throws HibernateException {
		if (sessionFactory == null) {
			// This should hopefully never happen....
			setup();
		}
		return sessionFactory.openSession();
	}
}
