package net.casian.craftmastery.block;

import net.casian.craftmastery.CraftMastery;
import net.casian.craftmastery.block.custom.MetalPressBlock;
import net.casian.craftmastery.block.custom.RollingMillBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block ROLLING_MILL_BLOCK = registerBlock("rolling_mill",
            new RollingMillBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block METAL_PRESS_BLOCK = registerBlock("metal_press",
            new MetalPressBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));


    public static void registerModBlock() {
        CraftMastery.LOGGER.info("Registering ModBlocks for " + CraftMastery.MOD_ID);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(CraftMastery.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(CraftMastery.MOD_ID, name),block);
    }
}
