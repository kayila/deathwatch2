package com.kitsuneindustries.deathwatch2.handler;

import static java.util.Objects.requireNonNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import java.util.Calendar;

import com.kitsuneindustries.deathwatch2.data.PlayerDeath;
import com.kitsuneindustries.deathwatch2.store.DeathRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DeathHandler {
	
	private static final Logger log = LogManager.getLogger(DeathHandler.class);
	
    private static DeathRegistry deathStore = new DeathRegistry();
    
    @SubscribeEvent
    public static void logDeath(LivingDeathEvent event) {
    	Entity entity = event.getEntity();
    	if (entity.world.isRemote) {
    		// Bail
    		return;
    	}
    	if (entity instanceof EntityPlayer) {
    		EntityPlayer player = (EntityPlayer) entity;
    		log.info(player.getName() + " DEATH HANDLER");
    		deathStore.persist(toPlayerDeath(event));
    	}
    }
    
    @VisibleForTesting
    static PlayerDeath toPlayerDeath(LivingDeathEvent event) {
    	requireNonNull(event);
    	// If the event isn't about a player, throw an exception
    	if (!(event.getEntityLiving() instanceof EntityPlayer)) {
    		throw new IllegalStateException("LivingDeathEvent entity must be a player");
    	}
    	
    	EntityPlayer player = (EntityPlayer) event.getEntityLiving();
    	DamageSource source = event.getSource();
    	
    	PlayerDeath death = new PlayerDeath();
    	death.setTimestamp(Calendar.getInstance().getTime());
    	death.setPlayerUUID(EntityPlayer.getUUID(player.getGameProfile()));
    	death.setPlayerName(player.getName());
    	death.setDimension(player.dimension);
    	death.setX(player.posX);
    	death.setY(player.posY);
    	death.setZ(player.posZ);
    	death.setType(source.damageType);
    	if (source.getTrueSource() != null) {
    		log.debug("source inner");
    		death.setKiller(source.getTrueSource().getName());
    	}
    	if (source.getDeathMessage(player) != null) {
    		log.debug("msg inner");
    		death.setMessage(source.getDeathMessage(player).getUnformattedText());
    	}

    	return death;
    }
    
}
