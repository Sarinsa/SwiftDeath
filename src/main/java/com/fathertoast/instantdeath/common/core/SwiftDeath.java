package com.fathertoast.instantdeath.common.core;

import fathertoast.crust.api.config.client.ClientConfigUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod( SwiftDeath.MODID )
public class SwiftDeath {
    
    /** The mod ID used by this mod. */
    public static final String MODID = "swiftdeath";
    
    @SuppressWarnings( "unused" )
    public SwiftDeath( FMLJavaModLoadingContext context ) {
        context.getModEventBus().addListener( this::onCommonSetup );
        MinecraftForge.EVENT_BUS.addListener( this::onLivingHurt );
        
        // Tell Forge to open the config editor when our mod's "Config" button is clicked in the Mods screen.
        DistExecutor.unsafeRunWhenOn( Dist.CLIENT, () -> () -> ClientConfigUtil.registerConfigButtonAsEditScreen( context.getContainer() ) );
    }
    
    private void onCommonSetup( FMLCommonSetupEvent event ) {
        event.enqueueWork( Config::initialize );
    }
    
    /**
     * Called when a living entity is set to be hurt.
     * If the event is canceled, the entity will not be hurt.
     * <br><br>
     * Here we check if the entity to hurt should be insta-killed
     * by whatever damage type is going to hurt it.
     */
    @SubscribeEvent( priority = EventPriority.LOWEST )
    @SuppressWarnings( "UnstableApiUsage" )
    public void onLivingHurt( LivingHurtEvent event ) {
        // noinspection resource
        if ( event.getEntity().level().isClientSide ) return;
        
        // Check if the damage type is in the list of insta-kill damage types.
        if( !Config.MAIN.instakillDamageTypes.contains( event.getSource().type() ) ) return;

        // Check if the entity type is in the list of entity types to insta-kill.
        if( Config.MAIN.affectedEntities.contains( event.getEntity().getType() ) )
            event.setAmount( Float.MAX_VALUE );
    }
}
