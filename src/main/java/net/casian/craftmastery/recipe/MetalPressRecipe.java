package net.casian.craftmastery.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.List;

public class MetalPressRecipe implements Recipe<SimpleInventory> {

    private final ItemStack output;
    private final List<Ingredient> recipeItems;
    private final int count;

    public MetalPressRecipe(List<Ingredient> recipeItems, int count, ItemStack output) {
        this.output = output;
        this.count = count;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient()) {
            return false;
        }

        return recipeItems.get(0).test(inventory.getStack(0)) && count <= inventory.getStack(0).getCount();
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(recipeItems);
        return list;
    }

    public int getCount() {
        return count;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MetalPressRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return MetalPressRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<MetalPressRecipe> {
        public static final MetalPressRecipe.Type INSTANCE = new MetalPressRecipe.Type();
        public static final String ID = "metal_press";
    }

    public static class Serializer implements RecipeSerializer<MetalPressRecipe> {

        public static final MetalPressRecipe.Serializer INSTANCE = new MetalPressRecipe.Serializer();
        public static final String ID = "metal_press";

        public static final Codec<MetalPressRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
                validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, 9).fieldOf("ingredients").forGetter(MetalPressRecipe::getIngredients),
                Codec.INT.fieldOf("count").orElse(1).forGetter(MetalPressRecipe::getCount),
                RecipeCodecs.CRAFTING_RESULT.fieldOf("output").forGetter(r -> r.output)
        ).apply(in, MetalPressRecipe::new));

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int max) {
            return Codecs.validate(Codecs.validate(
                    delegate.listOf(), list -> list.size() > max ? DataResult.error(() -> "Recipe has too many ingredients!") : DataResult.success(list)
            ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredients!") : DataResult.success(list));
        }

        @Override
        public Codec<MetalPressRecipe> codec() {
            return CODEC;
        }

        @Override
        public MetalPressRecipe read(PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            int count = buf.readInt();

            ItemStack output = buf.readItemStack();
            return new MetalPressRecipe(inputs, count, output);
        }

        @Override
        public void write(PacketByteBuf buf, MetalPressRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }

            buf.writeItemStack(recipe.getResult(null));
        }
    }
}