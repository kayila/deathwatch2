package com.kitsuneindustries.deathwatch2;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresWorldRestart;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;

@Mod.EventBusSubscriber
@Config(modid = Deathwatch2.MODID, category="")
public class ModConfig {
	
	public static final General general = new General();
	public static final WebServer webserver = new WebServer();

	public static class General {
	}

	public static class WebServer {

		@RequiresWorldRestart
		@Comment("Enable the embedded web server")
		public boolean enabled = true;

		@RequiresWorldRestart
		@Comment("Embedded web server port")
		public int port = 25566;
		
		@Comment("CORS Access-Control-Allow-Origin setting")
		public String corsAllowOrigin = "*";
	}

	public static boolean sync() {
		ConfigManager.sync(Deathwatch2.MODID, Config.Type.INSTANCE);
		return true;
	}

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (Deathwatch2.MODID.equals(event.getModID())) {
			sync();
		}
	}
}
