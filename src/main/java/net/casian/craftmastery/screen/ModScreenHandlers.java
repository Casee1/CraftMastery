package net.casian.craftmastery.screen;

import net.casian.craftmastery.CraftMastery;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static final ScreenHandlerType<RollingMillScreenHandler> ROLLING_MILL_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(CraftMastery.MOD_ID, "rolling_mill"),
                    new ExtendedScreenHandlerType<>(RollingMillScreenHandler::new));

    public static final ScreenHandlerType<MetalPressScreenHandler> METAL_PRESS_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(CraftMastery.MOD_ID, "metal_press"),
                    new ExtendedScreenHandlerType<>(MetalPressScreenHandler::new));

    public static void registerScreenHandlers() {
        CraftMastery.LOGGER.info("Registering Screen Handlers for " + CraftMastery.MOD_ID);
    }
}
