package net.casian.craftmastery.block.entity;

import net.casian.craftmastery.recipe.RollingMillRecipe;
import net.casian.craftmastery.screen.RollingMillScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RollingMillBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;


    public RollingMillBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROLLING_MILL_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> RollingMillBlockEntity.this.progress;
                    case 1 -> RollingMillBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> RollingMillBlockEntity.this.progress = value;
                    case 1 -> RollingMillBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Rolling Mill");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("rolling_mill.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("rolling_mill.progress");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RollingMillScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }


    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }

        if(isOutputSlotEmptyOrReceivable()) {
            if(this.hasRecipe()) {
                if(this.hasEnoughFuel()) {
                    this.increaseCraftProgress();
                    markDirty(world, pos, state);

                    if(hasCraftingFinished()) {
                        this.consumeFuel();
                        this.craftItem();
                        this.resetProgress();
                    }
                } else {
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem() {
        Optional<RecipeEntry<RollingMillRecipe>> recipe = getCurrentRecipe();
        recipe.ifPresent(r -> {
            this.removeStack(INPUT_SLOT, r.value().getCount());
            this.setStack(OUTPUT_SLOT, new ItemStack(
                    r.value().getResult(null).getItem(),
                    getStack(OUTPUT_SLOT).getCount() + r.value().getResult(null).getCount()
            ));
        });
    }

    private boolean hasCraftingFinished() {
        if(progress >= maxProgress) {
            return true;
        } else {
            return false;
        }
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<RollingMillRecipe>> recipe = getCurrentRecipe();

        if(recipe.isPresent() && canInsertAmountIntoOutputSlot(recipe.get().value().getResult(null))
                && canInsertItemIntoOutputSlot(recipe.get().value().getResult(null).getItem())) {
            return true;
        } else {
            return false;
        }
    }

    private Optional<RecipeEntry<RollingMillRecipe>> getCurrentRecipe() {
        SimpleInventory simpleInventory = new SimpleInventory(this.size());
        for(int i = 0; i < this.size(); i++) {
            simpleInventory.setStack(i, this.getStack(i));
        }

        return getWorld().getRecipeManager().getFirstMatch(RollingMillRecipe.Type.INSTANCE, simpleInventory, getWorld());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        if (this.getStack(OUTPUT_SLOT).getItem() == item) {
            return true;
        }
        if (this.getStack(OUTPUT_SLOT).isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();
        int resultCount = result.getCount();
        int maxCount = getStack(OUTPUT_SLOT).getMaxCount();

        if(currentCount + resultCount <= maxCount) {
            return true;
        }
        return false;
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        ItemStack itemStack = this.getStack(OUTPUT_SLOT);
        if(itemStack.isEmpty()) {
            return true;
        }

        if(itemStack.getCount() < itemStack.getMaxCount()) {
            return true;
        }
        return false;
    }

    public boolean isValidFuel(ItemStack itemStack ) {
        return itemStack.isOf(Items.COAL);
    }

    public boolean hasEnoughFuel() {
        ItemStack fuelStack = this.getStack(FUEL_SLOT);
        Optional<RecipeEntry<RollingMillRecipe>> recipe = getCurrentRecipe();

        if(!fuelStack.isEmpty() && fuelStack.getCount() >= recipe.get().value().getCount() + 1 && isValidFuel(fuelStack)) {
            return true;
        }
        return false;

    }

    public void consumeFuel() {
        Optional<RecipeEntry<RollingMillRecipe>> recipe = getCurrentRecipe();
        recipe.ifPresent(r -> {
            this.removeStack(FUEL_SLOT, r.value().getCount() + 1);
        });
    }
}
