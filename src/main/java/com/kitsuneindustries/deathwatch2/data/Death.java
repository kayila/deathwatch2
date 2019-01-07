package com.kitsuneindustries.deathwatch2.data;

public class Death {
	private final String id;
	private final Date timestamp;
	private final String playerUUID;
	private final String playerName;
	private final int dimension;
	private final double x;
	private final double y;
	private final double z;
	private final DamageSource type;
	private final Optional<String> killer;
	private final String message;
}
