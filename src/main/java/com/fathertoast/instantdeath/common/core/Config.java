package com.fathertoast.instantdeath.common.core;

import fathertoast.crust.api.config.common.AbstractConfigFile;
import fathertoast.crust.api.config.common.ConfigManager;
import fathertoast.crust.api.config.common.field.collection.RegistryListField;
import fathertoast.crust.api.config.common.field.collection.RegistrySetField;
import fathertoast.crust.api.config.common.value.collection.RegistryList;
import fathertoast.crust.api.config.common.value.collection.RegistrySet;
import fathertoast.crust.api.config.common.value.collection.key.IRegWrapper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@SuppressWarnings( "UnstableApiUsage" )
public class Config {
    
    private static final ConfigManager MANAGER = ConfigManager.create( "SwiftDeath", SwiftDeath.MODID );
    
    public static final MainConfig MAIN = new MainConfig( MANAGER, "main" );
    
    
    public static class MainConfig extends AbstractConfigFile.Simple {
        
        public final RegistrySetField<EntityType<?>> affectedEntities;
        public final RegistrySetField<DamageType> instakillDamageTypes;
        
        public MainConfig( ConfigManager cfgManager, String cfgName ) {
            super( cfgManager, cfgName,
                    "The primary config for this mod." );
            
            affectedEntities = SPEC.define( new RegistrySetField<>( "affected_entities", createDefaultAffectedEntities(),
                    "A list of entities that should get insta-killed by the below listed damage types.",
                    "By default, this only includes players." ) );
            
            instakillDamageTypes = SPEC.define( new RegistrySetField<>( "instakill_damage_types", createDefaultDamageTypes(),
                    "A list of IDs for damage types that should cause instant death.",
                    "By default, this includes drowning, starving or lava damage." ) );
        }
        
        private static RegistrySet<EntityType<?>> createDefaultAffectedEntities() {
            return new RegistrySet.Builder<>( ForgeRegistries.ENTITY_TYPES )
                    .add( EntityType.PLAYER )
                    .build();
        }
        
        private static RegistrySet<DamageType> createDefaultDamageTypes() {
            return new RegistrySet.Builder<>( Registries.DAMAGE_TYPE )
                    .add( DamageTypes.DROWN )
                    .add( DamageTypes.STARVE )
                    .add( DamageTypes.LAVA )
                    .build();
        }
    }
    
    /** Performs loading of configs in this mod. */
    public static void initialize() {
        MAIN.SPEC.initialize();
    }
}
