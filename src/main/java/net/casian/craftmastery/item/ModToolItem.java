package net.casian.craftmastery.item;

import net.casian.craftmastery.CraftMastery;
import net.casian.craftmastery.item.custom.LumberAxeItem;
import net.casian.craftmastery.item.custom.MiningHammerItem;
import net.casian.craftmastery.item.custom.MiningPickaxeItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModToolItem {

    public static final MiningToolItem LUMBER_AXE = registerItem("lumber_axe",
            new LumberAxeItem(new FabricItemSettings().maxDamage(1328)));

    public static final MiningToolItem MINING_HAMMER = registerItem("mining_hammer",
            new MiningHammerItem(new FabricItemSettings().maxDamage(2166)));

    public static final MiningToolItem MINING_PICKAXE = registerItem("mining_pickaxe",
            new MiningPickaxeItem(new FabricItemSettings().maxDamage(1328)));

    public static void registerModToolItems() {
        CraftMastery.LOGGER.info("Registering Mod Items for " + CraftMastery.MOD_ID);
    }


    private static MiningToolItem registerItem(String name, MiningToolItem item) {
        return Registry.register(Registries.ITEM, new Identifier(CraftMastery.MOD_ID, name),item);
    }

}
