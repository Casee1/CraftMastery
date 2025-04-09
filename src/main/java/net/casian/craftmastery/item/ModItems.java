package net.casian.craftmastery.item;

import net.casian.craftmastery.CraftMastery;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item TOOL_ROD = registerItem("tool_rod", new Item(new FabricItemSettings()));

    public static final Item TOOL_PLATE = registerItem("tool_plate", new Item(new FabricItemSettings()));

    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(TOOL_ROD);
    }

    public static void registerModItems() {
        CraftMastery.LOGGER.info("Registering Mod Items for " + CraftMastery.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(CraftMastery.MOD_ID, name),item);
    }
}
