package com.kitsuneindustries.deathwatch2.proxy;

import java.io.File;

import javax.annotation.Nullable;

import com.kitsuneindustries.deathwatch2.Config;
import com.kitsuneindustries.deathwatch2.Deathwatch2;
import com.kitsuneindustries.deathwatch2.command.DWCommand;
import com.kitsuneindustries.deathwatch2.store.PersistanceHelper;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod.EventBusSubscriber
public class CommonProxy {
	
	@Nullable private static Configuration config;
	
	@Nullable
	public static Configuration getConfig() {
		return config;
	}
	
	public void preInit(FMLPreInitializationEvent event) {
		Deathwatch2.getLogger().info("CommonProxy preInit");
		File directory = event.getModConfigurationDirectory();
		config = new Configuration(new File(directory.getPath(), "deathwatch2.cfg"));
		Config.readConfig();
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		Deathwatch2.getLogger().info("CommonProxy postInit");
		if (config.hasChanged()) {
			config.save();
		}
	}

	public void serverLoad(FMLServerStartingEvent event) {
		PersistanceHelper.setup();
		event.registerServerCommand(new DWCommand());
	}

}
