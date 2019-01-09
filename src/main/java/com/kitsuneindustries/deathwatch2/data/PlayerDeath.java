package com.kitsuneindustries.deathwatch2.data;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;

@javax.persistence.Entity
@Table(name = "playerdeaths", uniqueConstraints = {@UniqueConstraint(columnNames = { "ID" })})
public class PlayerDeath {
	
	public PlayerDeath() {
		id = UUID.randomUUID();
		timestamp = Calendar.getInstance().getTime();
	}
	
	@Id
	@Column(updatable = false, nullable = false)
	private UUID id;
	
	@Column
	private Date timestamp;
	
	@Column
	private UUID playerUUID;
	
	@Column
	private String playerName;
	
	@Column
	private int dimension;
	
	@Column
	private double x;
	
	@Column
	private double y;
	
	@Column
	private double z;
	
	@Column(nullable = true)
	private String type;
	
	@Column(nullable = true)
	private String killer;
	
	@Column(nullable = true)
	private String message;
	
	public void setId(UUID id) { this.id = requireNonNull(id); }
	public UUID getId() { return id; }
	
	public void setTimestamp(Date timestamp) { this.timestamp = requireNonNull(timestamp); }
	public Date getTimestamp() { return timestamp; }
	
	public void setPlayerUUID(UUID playerUUID) { this.playerUUID = requireNonNull(playerUUID); }
	public UUID getPlayerUUID() { return playerUUID; }
	
	public void setPlayerName(String name) { this.playerName = requireNonNull(name); }
	public String getPlayerName() { return playerName; }
	
	public void setDimension(int dimension) { this.dimension = dimension; }
	public int getDimension() { return dimension; }
	
	public void setX(double x) { this.x = x; }
	public double getX() { return x; }
	
	public void setY(double y) { this.y = y; }
	public double getY() { return y; }
	
	public void setZ(double z) { this.z = z; }
	public double getZ() { return z; }
	
	public void setType(String type) { this.type = type; } 
	public String getType() { return type; }
	
	public void setKiller(String killer) { this.killer = killer; }
	public String getKiller() { return killer; }
	
	public void setMessage(String message) { this.message = message; }
	public String getMessage() { return message; }
	
	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || !(o instanceof PlayerDeath)) { return false; }
		
		PlayerDeath other = (PlayerDeath)o;
		
		return Objects.equals(id, other.id)
				// This is stupid, but for some reason it's not detecting that the timestamps are equal otherwise
				&& Objects.equals(timestamp.getTime(), other.timestamp.getTime())
				&& Objects.equals(playerUUID, other.playerUUID)
				&& Objects.equals(playerName, other.playerName)
				&& Objects.equals(dimension, other.dimension)
				&& Objects.equals(x, other.x)
				&& Objects.equals(y, other.y)
				&& Objects.equals(z, other.z)
				&& Objects.equals(type, other.type)
				&& Objects.equals(killer, other.killer)
				&& Objects.equals(message, other.message);
	}
	
	@Override
	public String toString() {
		return String.format("%d - %s(%s) dim:%d [%f, %f, %f] %s (%s) (%s - %s)", timestamp.getTime(), playerName, playerUUID, dimension, x, y, z, message, id, type, killer);
	}
}
