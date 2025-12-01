package com.fathertoast.instantdeath.common.core;

import fathertoast.crust.api.config.common.AbstractConfigCategory;
import fathertoast.crust.api.config.common.AbstractConfigFile;
import fathertoast.crust.api.config.common.ConfigManager;
import fathertoast.crust.api.config.common.field.PredicateStringListField;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class Config {
    
    private static final ConfigManager MANAGER = ConfigManager.create( "SwiftDeath", SwiftDeath.MODID );
    
    public static final MainConfig MAIN = new MainConfig( MANAGER, "main" );
    
    
    public static class MainConfig extends AbstractConfigFile {
        
        public GeneralCategory GENERAL;
        
        public MainConfig( ConfigManager cfgManager, String cfgName ) {
            super( cfgManager, cfgName,
                    "The primary config for this mod." );
            
            GENERAL = new GeneralCategory( this );
        }
        
        public static class GeneralCategory extends AbstractConfigCategory<MainConfig> {
            
            public final PredicateStringListField affectedEntities;
            public final PredicateStringListField instakillDamageTypes;
            
            public GeneralCategory( MainConfig parent ) {
                super( parent, "general", "General settings for Instant Death." );
                
                affectedEntities = SPEC.define( new PredicateStringListField( "affected_entities", "entity type ID", makeDefaultAffectedEntities(),
                        GeneralCategory::isValidId,
                        "A list of IDs for entity types that should get insta-killed by the below listed damage types.",
                        "By default, this only includes players." ) );
                
                instakillDamageTypes = SPEC.define( new PredicateStringListField( "instakill_damage_types", "damage type ID", makeDefaultDamageTypes(),
                        GeneralCategory::isValidId,
                        "A list of IDs for damage types that should cause instant death.",
                        "By default, this includes drowning, starving or lava damage." ) );
            }
            
            private List<String> makeDefaultAffectedEntities() {
                // noinspection ConstantConditions
                return List.of(
                        ForgeRegistries.ENTITY_TYPES.getKey( EntityType.PLAYER ).toString()
                );
            }
            
            private List<String> makeDefaultDamageTypes() {
                return List.of(
                        DamageTypes.DROWN.location().toString(),
                        DamageTypes.STARVE.location().toString(),
                        DamageTypes.LAVA.location().toString()
                );
            }
            
            /**
             * @return True if the given string contains a namespace and path separated by ':',
             * and passes the {@link ResourceLocation#isValidResourceLocation(String)} check.
             */
            private static boolean isValidId( String value ) {
                String[] parts = value.split( ":" );
                if( parts.length != 2 ) return false;
                
                return ResourceLocation.isValidResourceLocation( value );
            }
        }
    }
    
    /** Performs loading of configs in this mod. Added to deferred work queue at common setup. */
    public static void initialize() {
        MAIN.SPEC.initialize();
    }
}
