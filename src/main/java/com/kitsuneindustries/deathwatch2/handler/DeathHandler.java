package com.kitsuneindustries.deathwatch2.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.Logger;

import com.kitsuneindustries.deathwatch2.Deathwatch2;
import com.kitsuneindustries.deathwatch2.store.DeathStore;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DeathHandler {
	
	private static final Logger log = Deathwatch2.getLogger();

    private static FileWriter fileWriter = null;
    private static File file = null;
    
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd.HHmmss");
	
    private static FileWriter getWriter() throws IOException {
    	if (file == null) {
    		// TODO Support other filewriter formats
    		file = new File(DimensionManager.getCurrentSaveRootDirectory(), "deathwatch.csv");
    	}
    	
    	if (fileWriter == null) {
    		fileWriter = new FileWriter(file, true);
    	}
    	
    	return fileWriter;
    }
    
    @SubscribeEvent
    public static void logDeath(LivingDeathEvent event) {
    	Entity entity = event.getEntity();
    	if (entity instanceof EntityPlayer) {
    		EntityPlayer player = (EntityPlayer) entity;
    		log.info(player.getDisplayNameString() + " died");
    		DeathStore.getInstance().persistDeath(event);
    	}
    }
    
}
