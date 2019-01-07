package com.kitsuneindustries.deathwatch2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.kitsuneindustries.deathwatch2.proxy.CommonProxy;

import net.minecraftforge.common.config.Configuration;

public class Config {
	
	private static final Logger log = Deathwatch2.getLogger();
	
	private static final String CATEGORY_GENERAL = "general";
	private static final String CATEGORY_COMMAND = "commands";
	
	public static enum Format {
		CSV, JSON, TSV, NONE;
	}
	
	private static Format outputFormat = Format.CSV;
	private static boolean startServer = false;
	private static int serverPort = -1;
	
	public static boolean getStartServer() { return startServer; }
	public static int getServerPort() { return serverPort; }
	
	public static void readConfig() {
		log.debug("Loading config");
		Configuration cfg = CommonProxy.getConfig();
		try {
			cfg.load();
			generalConfig(cfg);
			commandConfig(cfg);
		} catch (Exception e) {
			log.error("Error loading config file", e);
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
		log.debug("Config loaded");
	}
	
	private static void generalConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General");
		
		List<String> formats = Stream.of(Format.values())
				.map(Format::name)
				.collect(Collectors.toList());
		outputFormat = Format.valueOf(cfg.getString("format", CATEGORY_GENERAL, outputFormat.toString(),
				"Format of output file [" + String.join(", ", formats + "]")));
		
		startServer = cfg.getBoolean("startServer", CATEGORY_GENERAL, startServer, "Start the embedded Deathwatch API server");
		serverPort = cfg.getInt("serverPort", CATEGORY_GENERAL, serverPort, -1, 65535, "Port to start the embedded API server on");
		
	}
	
	private static void commandConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_COMMAND, "Commands");
		
	}

}
