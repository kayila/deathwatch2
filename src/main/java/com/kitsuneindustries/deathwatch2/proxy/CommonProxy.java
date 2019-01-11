package com.kitsuneindustries.deathwatch2.proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import com.kitsuneindustries.deathwatch2.ModConfig;
import com.kitsuneindustries.deathwatch2.command.DWCommand;
import com.kitsuneindustries.deathwatch2.store.PersistanceHelper;
import com.kitsuneindustries.deathwatch2.web.DeathwatchServlet;

public class CommonProxy {
	
	private static final Logger log = LogManager.getLogger(CommonProxy.class);
	
	public void preInit(FMLPreInitializationEvent event) {
		ModConfig.sync();
	}
	
	public void postInit(FMLPostInitializationEvent event) {
	}

	public void serverLoad(FMLServerStartingEvent event) {
		PersistanceHelper.setup();
		event.registerServerCommand(new DWCommand());
		
		if (ModConfig.webserver.enabled) {
			// Start the embedded web server
			Server webserver = new Server(ModConfig.webserver.port);
			ServletHandler servletHandler = new ServletHandler();
			webserver.setHandler(servletHandler);
			servletHandler.addServletWithMapping(DeathwatchServlet.class, "/*");
			//servletHandler.
			try {
				webserver.start();
			} catch (Exception e) {
				log.error("Error starting embedded web server", e);
				
			}
		}
	}

}
