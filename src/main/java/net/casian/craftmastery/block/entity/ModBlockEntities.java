package net.casian.craftmastery.block.entity;

import com.sun.jna.platform.unix.solaris.LibKstat;
import net.casian.craftmastery.CraftMastery;
import net.casian.craftmastery.block.ModBlocks;
import net.casian.craftmastery.block.custom.RollingMillBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<GemPolishingStationBlockEntity> GEM_POLISHING_STATION_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CraftMastery.MOD_ID, "gem_polishing_be"),
                    FabricBlockEntityTypeBuilder.create(GemPolishingStationBlockEntity::new,
                        ModBlocks.GEM_POLISHING_BLOCK).build());

    public static final BlockEntityType<RollingMillBlockEntity> ROLLING_MILL_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CraftMastery.MOD_ID, "rolling_mill"),
                    FabricBlockEntityTypeBuilder.create(RollingMillBlockEntity::new,
                            ModBlocks.ROLLING_MILL_BLOCK).build());

    public static final BlockEntityType<MetalPressBlockEntity> METAL_PRESS_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CraftMastery.MOD_ID, "metal_press"),
                    FabricBlockEntityTypeBuilder.create(MetalPressBlockEntity::new,
                            ModBlocks.METAL_PRESS_BLOCK).build());

    public static void registerBlockEntities() {
        CraftMastery.LOGGER.info("Registring Block Entities for " + CraftMastery.MOD_ID);
    }
}
