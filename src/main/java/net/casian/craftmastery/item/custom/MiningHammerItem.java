package net.casian.craftmastery.item.custom;

import net.casian.craftmastery.utils.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MiningHammerItem extends MiningToolItem {


    public MiningHammerItem(Settings settings) {
        super(1, -2.8F, ToolMaterials.IRON, ModTags.Blocks.MINING_HAMMER, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if(!world.isClient()) {
            PlayerEntity player = MinecraftClient.getInstance().player;
            Direction direction = player.getHorizontalFacing();
            float pitch = player.getPitch();

            int di[];
            int dj[];
            int dz[];
            int nrOfBlocksCut = 0;
            if(pitch < -45 || pitch > 45){ //inseamna ca mineaza in jos sau un sus
                di = new int[] {-1,0,1};
                dj = new int[] {0,0,0};
                dz = new int[] {-1,0,1};
                System.out.println("sus jos");
            } else if(direction == Direction.NORTH || direction == Direction.SOUTH) {
                di = new int[] {-1,0,1};
                dj = new int[] {-1,0,1};
                dz = new int[] {0,0,0};
                System.out.println("nord sud");
            } else if(direction == Direction.EAST || direction == Direction.WEST){
                di = new int[] {0,0,0};
                dj = new int[] {-1,0,1};
                dz = new int[] {-1,0,1};
                System.out.println("est vest");
            } else {
                di = new int[] {0,0,0};
                dj = new int[] {0,0,0};
                dz = new int[] {0,0,0};
            }


            world.breakBlock(pos,true);


            BlockState initialBlockState = state;

            for(int i=0; i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    for(int z=0; z<3;z++)
                    {
                        BlockPos currentPos = pos.add(di[i],dj[j],dz[z]);
                        BlockState currentBlockState = world.getBlockState(currentPos);

                        if(initialBlockState == currentBlockState) {
                            world.breakBlock(currentPos, true);
                            nrOfBlocksCut++;
                        }
                    }
                }
            }

            stack.damage(nrOfBlocksCut, miner,
                    playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
        }

        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.craftmastery.mining_hammer.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
