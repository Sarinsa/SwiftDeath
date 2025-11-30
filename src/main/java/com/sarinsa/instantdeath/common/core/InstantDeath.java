package com.sarinsa.instantdeath.common.core;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod( InstantDeath.MODID )
public class InstantDeath {
    
    /** The mod ID used by this mod. */
    public static final String MODID = "instantdeath";
    
    /** The logger used by this mod. */
    private static final Logger LOGGER = LogUtils.getLogger();
    
    
    public InstantDeath( FMLJavaModLoadingContext context ) {
        IEventBus modEventBus = context.getModEventBus();
    }
}
