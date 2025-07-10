package net.casian.craftmastery.item.custom;

import net.casian.craftmastery.utils.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class LumberAxeItem extends MiningToolItem {
    public LumberAxeItem(Settings settings) {
        super(6.0F, -3.1F, ToolMaterials.IRON, ModTags.Blocks.LUMBER_AXE, settings);
    }


    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {

        if(!world.isClient() && isWoodBlock(state)) {

            Stack<Pair<BlockPos, BlockState>> stackPosition  = new Stack<>();
            Set<BlockPos> visited = new HashSet<>();

            BlockState initialBlockState = state;

            stackPosition.push(new Pair<>(pos, state));

            int nrOfBlocksCut = 0;

            int di[] = {-1, 1};
            int dz[] = {-1, 1};

            while(!stackPosition.isEmpty()) {
                Pair<BlockPos, BlockState> currentBlock = stackPosition.pop();
                BlockPos currentBlockPos = currentBlock.getLeft();
                BlockState currentBlockState = currentBlock.getRight();

                if(visited.contains(currentBlockPos)) {
                    continue;
                }

                visited.add(currentBlockPos);

                if(isTreeBlock(currentBlockState)) {
                    stackPosition.push(new Pair<>(currentBlockPos.up(), world.getBlockState(currentBlockPos.up())));
                    stackPosition.push(new Pair<>(currentBlockPos.down(), world.getBlockState(currentBlockPos.down())));
                    stackPosition.push(new Pair<>(currentBlockPos.north(), world.getBlockState(currentBlockPos.north())));
                    stackPosition.push(new Pair<>(currentBlockPos.south(), world.getBlockState(currentBlockPos.south())));
                    stackPosition.push(new Pair<>(currentBlockPos.west(), world.getBlockState(currentBlockPos.west())));
                    stackPosition.push(new Pair<>(currentBlockPos.east(), world.getBlockState(currentBlockPos.east())));


                    for(int i=0;i<2;i++) {
                        for(int z=0;z<2;z++) {
                            stackPosition.push(new Pair<>(currentBlockPos.add(di[i],0,dz[z]), world.getBlockState(currentBlockPos.add(di[i],0,dz[z]))));
                        }
                    }
                }

                if(isWoodBlock(currentBlockState) && initialBlockState.getBlock() == currentBlockState.getBlock()) {
                    world.breakBlock(currentBlockPos, true);
                    nrOfBlocksCut++;

                }
            }

            stack.damage(nrOfBlocksCut, miner,
                    playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
        }

        return true;

    }

    private boolean isWoodBlock(BlockState blockState) {
        return blockState.isIn(ModTags.Blocks.LUMBER_AXE);
    }

    private boolean isLeafBlock(BlockState blockState) {
        return blockState.isIn(ModTags.Blocks.LEAVES);
    }

    private boolean isTreeBlock(BlockState blockState) {
        return isWoodBlock(blockState) || isLeafBlock(blockState);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.craftmastery.lumber_axe.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
