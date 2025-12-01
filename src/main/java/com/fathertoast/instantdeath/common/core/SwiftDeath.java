package com.fathertoast.instantdeath.common.core;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.Objects;


@Mod( SwiftDeath.MODID )
public class SwiftDeath {
    
    /** The mod ID used by this mod. */
    public static final String MODID = "swiftdeath";
    
    /** The logger used by this mod. */
    private static final Logger LOGGER = LogUtils.getLogger();
    
    
    public SwiftDeath( FMLJavaModLoadingContext context ) {
        Config.initialize();
        MinecraftForge.EVENT_BUS.addListener( this::onLivingHurt );
    }
    
    /**
     * Called when a living entity is set to be hurt.
     * If the event is canceled, the entity will not be hurt.
     * <br><br>
     * Here we check if the entity to hurt should be insta-killed
     * by whatever damage type is going to hurt it.
     */
    @SubscribeEvent( priority = EventPriority.LOWEST )
    public void onLivingHurt( LivingHurtEvent event ) {
        // noinspection resource
        final String typeId = Objects.requireNonNull( event.getEntity().level().registryAccess().registryOrThrow( Registries.DAMAGE_TYPE )
                .getKey( event.getSource().type() ) ).toString();
        
        // Check if the damage type is in the list of insta-kill damage types.
        if( !Config.MAIN.GENERAL.instakillDamageTypes.get().contains( typeId ) ) return;
        
        final String entityTypeId = Objects.requireNonNull( ForgeRegistries.ENTITY_TYPES.getKey( event.getEntity().getType() ) ).toString();
        
        // Check if the entity type is in the list of entity types to insta-kill.
        if( Config.MAIN.GENERAL.affectedEntities.get().contains( entityTypeId ) )
            event.setAmount( Float.MAX_VALUE );
    }
}
