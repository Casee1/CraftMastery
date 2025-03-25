package net.casian.craftmastery;

import net.casian.craftmastery.screen.GemPolishingScreen;
import net.casian.craftmastery.screen.MetalPressScreen;
import net.casian.craftmastery.screen.ModScreenHandlers;
import net.casian.craftmastery.screen.RollingMillScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class CraftMasteryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HandledScreens.register(ModScreenHandlers.GEM_POLISHING_SCREEN_HANDLER, GemPolishingScreen::new);
        HandledScreens.register(ModScreenHandlers.ROLLING_MILL_SCREEN_HANDLER, RollingMillScreen::new);
        HandledScreens.register(ModScreenHandlers.METAL_PRESS_SCREEN_HANDLER, MetalPressScreen::new);

    }
}
