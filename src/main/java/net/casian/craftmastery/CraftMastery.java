package net.casian.craftmastery;

import net.casian.craftmastery.block.ModBlocks;
import net.casian.craftmastery.block.entity.ModBlockEntities;
import net.casian.craftmastery.item.ModItemGroups;
import net.casian.craftmastery.item.ModItems;
import net.casian.craftmastery.item.ModToolItem;
import net.casian.craftmastery.recipe.ModRecipes;
import net.casian.craftmastery.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CraftMastery implements ModInitializer {
	public static final String MOD_ID = "craftmastery";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		ModBlocks.registerModBlock();
		ModToolItem.registerModToolItems();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();

		ModRecipes.registerRecipes();
	}
}