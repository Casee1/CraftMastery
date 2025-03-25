package net.casian.craftmastery.utils;

import net.casian.craftmastery.CraftMastery;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    public static class Blocks {

        public static final TagKey<Block> METAL_DETECTOR_DETECTABLE_BLOCKS =
                createTag("metal_detector_detectable_blocks");

        public static final TagKey<Block> MINING_HAMMER =
                createTag("mineable/mining_hammer");

        public static final TagKey<Block> LUMBER_AXE =
                createTag("mineable/lumber_axe");

        public static final TagKey<Block> MINING_PICKAXE =
                createTag("mineable/mining_pickaxe");

        public static final TagKey<Block> LEAVES =
                createTag("leaves");

        public static final TagKey<Block> ORE_BLOCKS =
                createTag("ore_blocks");


        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(CraftMastery.MOD_ID, name));
        }

    }

    public static class Items {


        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(CraftMastery.MOD_ID, name));
        }

    }
}
