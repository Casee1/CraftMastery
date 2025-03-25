package net.casian.craftmastery.item.custom;

import net.casian.craftmastery.utils.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class MiningPickaxeItem extends MiningToolItem {

    public MiningPickaxeItem(Settings settings) {
        super(1,-2.8F,ToolMaterials.IRON, ModTags.Blocks.MINING_PICKAXE,settings);
    }


    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if(!world.isClient() && isMineableBlock(state)) {
            int nrOfBlocksCut = 0;
            if (isOreBlock(state)) {
                Stack<Pair<BlockPos, BlockState>> stackPosition  = new Stack<>();
                Set<BlockPos> visited = new HashSet<>();

                BlockState initialBlockState = state;

                stackPosition.push(new Pair<>(pos, state));



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

                    if(isMineableBlock(currentBlockState) && initialBlockState == currentBlockState) {
                        world.breakBlock(currentBlockPos, true);

                        nrOfBlocksCut++;

                        stackPosition.push(new Pair<>(currentBlockPos.up(), world.getBlockState(currentBlockPos.up())));
                        stackPosition.push(new Pair<>(currentBlockPos.down(), world.getBlockState(currentBlockPos.down())));
                        stackPosition.push(new Pair<>(currentBlockPos.north(), world.getBlockState(currentBlockPos.north())));
                        stackPosition.push(new Pair<>(currentBlockPos.south(), world.getBlockState(currentBlockPos.south())));
                        stackPosition.push(new Pair<>(currentBlockPos.west(), world.getBlockState(currentBlockPos.west())));
                        stackPosition.push(new Pair<>(currentBlockPos.east(), world.getBlockState(currentBlockPos.east())));

                        for(int i=0;i<2;i++) {
                            for(int z=0;z<2;z++) {
                                System.out.println("Position of the current block:" + currentBlockPos);
                                System.out.println("Position of the block2: " + currentBlockPos.add(di[i],0,dz[z]));
                                stackPosition.push(new Pair<>(currentBlockPos.add(di[i],0,dz[z]), world.getBlockState(currentBlockPos.add(di[i],0,dz[z]))));
                            }
                        }
                    }
                }
            } else {
                world.breakBlock(pos, true);
                nrOfBlocksCut++;
            }

            stack.damage(nrOfBlocksCut, miner,
                    playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
        }

        return super.postMine(stack, world, state, pos, miner);
    }

    private boolean isMineableBlock(BlockState blockState) {
        return blockState.isIn(ModTags.Blocks.MINING_PICKAXE);
    }

    private boolean isOreBlock(BlockState blockState) {
        return blockState.isIn(ModTags.Blocks.ORE_BLOCKS);
    }
}
