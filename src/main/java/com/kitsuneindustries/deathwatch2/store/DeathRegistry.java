package com.kitsuneindustries.deathwatch2.store;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// I'd like to eventually use EntityManger, which is why it's still here.
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kitsuneindustries.deathwatch2.data.PlayerDeath;

import org.hibernate.Session;

public class DeathRegistry {
	
	private static final Logger log = LogManager.getLogger(DeathRegistry.class); 
	
	public void persist( PlayerDeath death ) {
		// TODO Check if world is remote, bail if it is
		Session session = PersistanceHelper.getSession();
		session.beginTransaction();
		session.persist(death);
		session.getTransaction().commit();
		session.close();
		log.info("Saved playerdeath as " + death.getId());
	}
	
	public List<PlayerDeath> findAll() {
		Session session = PersistanceHelper.getSession();
		TypedQuery<PlayerDeath> deaths = session.createQuery("from PlayerDeath", PlayerDeath.class);
		return deaths.getResultList();
	}
	
	public Optional<PlayerDeath> find(UUID id) {
		try (Session session = PersistanceHelper.getSession()) {
			TypedQuery<PlayerDeath> death = session.createQuery("from PlayerDeath as d where d.id = :id", PlayerDeath.class);
			death.setParameter("id", id);
			return Optional.of(death.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
	
	public List<PlayerDeath> findByPlayerUUID(UUID uuid) {
		Session session = PersistanceHelper.getSession();
		TypedQuery<PlayerDeath> deaths = session.createQuery("from PlayerDeath d where d.playerUUID = :uuid", PlayerDeath.class);
		deaths.setParameter("uuid", uuid);
		return deaths.getResultList();
	}
	
	public List<PlayerDeath> findByPlayerName(String name) {
		try (Session session = PersistanceHelper.getSession()) {
			TypedQuery<PlayerDeath> deaths = session.createQuery("from PlayerDeath d where d.playerName = :name", PlayerDeath.class);
			deaths.setParameter("name", name);
			return deaths.getResultList();
		}
	}
	
	public Optional<PlayerDeath> findLastDeathByName(String name) {
		try (Session session = PersistanceHelper.getSession()) {
			try {
				TypedQuery<PlayerDeath> death = session.createQuery("from PlayerDeath d where d.playerName = :name order by d.timestamp desc", PlayerDeath.class);
				death.setMaxResults(1);
				death.setParameter("name", name);
				return Optional.of(death.getSingleResult());
			} catch (NoResultException e) {
				return Optional.empty();
			}
		}
	}

}
