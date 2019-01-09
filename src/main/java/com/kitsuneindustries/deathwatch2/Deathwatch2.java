package com.kitsuneindustries.deathwatch2;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.Logger;

import com.kitsuneindustries.deathwatch2.proxy.CommonProxy;

@Mod(modid = Deathwatch2.MODID, name = Deathwatch2.NAME, version = Deathwatch2.VERSION, acceptableRemoteVersions = "*")
public class Deathwatch2
{
    public static final String MODID = "deathwatch2";
    public static final String NAME = "Deathwatch 2";
    public static final String VERSION = "1.0";
    
    @SidedProxy(clientSide = "com.kitsuneindustries.deathwatch2.proxy.CommonProxy",
    		serverSide = "com.kitsuneindustries.deathwatch2.proxy.CommonProxy")
    private static CommonProxy proxy;

    private static Logger logger;
    
    public static Logger getLogger() {
    	return logger;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
        
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
    	proxy.serverLoad(event);
    }
}
