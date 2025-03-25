package net.casian.craftmastery.recipe;

import net.casian.craftmastery.CraftMastery;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(CraftMastery.MOD_ID, GemPolishingRecipe.Serializer.ID),
                GemPolishingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(CraftMastery.MOD_ID, GemPolishingRecipe.Type.ID),
                GemPolishingRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(CraftMastery.MOD_ID, MetalPressRecipe.Serializer.ID),
                MetalPressRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(CraftMastery.MOD_ID, MetalPressRecipe.Type.ID),
                MetalPressRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(CraftMastery.MOD_ID, RollingMillRecipe.Serializer.ID),
                RollingMillRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(CraftMastery.MOD_ID, RollingMillRecipe.Type.ID),
                RollingMillRecipe.Type.INSTANCE);
    }
}
