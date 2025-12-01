package com.sarinsa.instantdeath.common.core;

import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Config {
    
    public static final MainConfig CONFIG;
    public static final ForgeConfigSpec CONFIG_SPEC;
    
    static {
        Pair<MainConfig, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure( MainConfig::new );
        CONFIG = commonPair.getLeft();
        CONFIG_SPEC = commonPair.getRight();
    }
    
    public static final class MainConfig {
        
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> instaKillDamageTypes;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> affectedEntities;
        
        private MainConfig( ForgeConfigSpec.Builder configBuilder ) {
            configBuilder.push( "general" );
            instaKillDamageTypes = configBuilder.comment( "A list of damage types that should instantly kill players." )
                    .define( "insta_kill_damage_types", defaultDamageTypes() );
            
            affectedEntities = configBuilder.comment( "A list of entity types that should be insta-killed by the damage types in the \"insta_kill_damage_types\" list." )
                    .define( "affected_entities", List.of( "minecraft:player" ) );
            configBuilder.pop();
        }
        
        private List<? extends String> defaultDamageTypes() {
            return List.of(
                    DamageTypes.DROWN.location().toString(),
                    DamageTypes.STARVE.location().toString(),
                    DamageTypes.LAVA.location().toString()
            );
        }
    }
}
