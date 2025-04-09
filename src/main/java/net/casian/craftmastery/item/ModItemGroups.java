package net.casian.craftmastery.item;

import net.casian.craftmastery.CraftMastery;
import net.casian.craftmastery.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup RUBY_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(CraftMastery.MOD_ID, "ruby"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.ruby"))
                    .icon(() -> new ItemStack(ModItems.TOOL_ROD)).entries((displayContext, entries) -> {


                        entries.add(ModToolItem.LUMBER_AXE);
                        entries.add(ModToolItem.MINING_HAMMER);
                        entries.add(ModToolItem.MINING_PICKAXE);

                        entries.add(ModBlocks.ROLLING_MILL_BLOCK);
                        entries.add(ModBlocks.METAL_PRESS_BLOCK);

                        entries.add(ModItems.TOOL_ROD);
                        entries.add(ModItems.TOOL_PLATE);



                    }).build());


    public static void registerItemGroups() {
        CraftMastery.LOGGER.info("Registering Item Groups for " + CraftMastery.MOD_ID);
    }
}
