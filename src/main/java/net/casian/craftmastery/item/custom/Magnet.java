package net.casian.craftmastery.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class Magnet extends Item {
    public Magnet(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if(!world.isClient) {
            double range = 5.0f;
            Box box = new Box(user.getBlockPos()).expand(range);

            List<ItemEntity> items = world.getEntitiesByClass(ItemEntity.class, box, itemEntity ->
                    !itemEntity.cannotPickup() && !itemEntity.isRemoved());

            int count = 0;

            for(ItemEntity item: items) {
                ItemStack itemStack = item.getStack();

                user.getInventory().insertStack(itemStack);
                count++;
            }

            ItemStack itemStack = user.getStackInHand(hand);
            itemStack.damage(count, user,
                    playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.craftmastery.magnet.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
