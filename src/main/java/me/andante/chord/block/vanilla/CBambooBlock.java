package me.andante.chord.block.vanilla;

import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class CBambooBlock extends BambooBlock {
    private final Supplier<? extends CBambooBlock> bambooBlock;
    private final Supplier<? extends CBambooSaplingBlock> saplingBlock;

    public CBambooBlock(Supplier<? extends CBambooBlock> bambooBlock, Supplier<? extends CBambooSaplingBlock> saplingBlock, Settings settings) {
        super(settings);
        this.bambooBlock = bambooBlock;
        this.saplingBlock = saplingBlock;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        if (!fluidState.isEmpty()) {
            return null;
        } else {
            BlockState blockStateDown = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
            if (blockStateDown.isIn(BlockTags.BAMBOO_PLANTABLE_ON)) {
                if (blockStateDown.isOf(this.getSaplingBlock())) {
                    return this.getDefaultState().with(AGE, 0);
                } else if (blockStateDown.isOf(this.getBambooBlock())) {
                    int age = blockStateDown.get(AGE) > 0 ? 1 : 0;
                    return this.getDefaultState().with(AGE, age);
                } else {
                    BlockState blockStateUp = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
                    return !blockStateUp.isOf(this.getBambooBlock()) && !blockStateUp.isOf(this.getSaplingBlock())
                        ? this.getSaplingBlock().getDefaultState()
                        : this.getDefaultState().with(AGE, blockStateUp.get(AGE));
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (!state.canPlaceAt(world, pos)) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }

        if (direction == Direction.UP && newState.isOf(this.getBambooBlock()) && newState.get(AGE) > state.get(AGE)) {
            world.setBlockState(pos, state.cycle(AGE), 2);
        }

        return state;
    }

    @Override
    protected void updateLeaves(BlockState state, World world, BlockPos pos, Random random, int height) {
        BlockState blockStateDown = world.getBlockState(pos.down());
        BlockPos blockPosDown2 = pos.down(2);
        BlockState blockStateDown2 = world.getBlockState(blockPosDown2);
        BambooLeaves bambooLeaves = BambooLeaves.NONE;
        if (height >= 1) {
            if (blockStateDown.isOf(this.getBambooBlock()) && blockStateDown.get(LEAVES) != BambooLeaves.NONE) {
                if (blockStateDown.isOf(this.getBambooBlock()) && blockStateDown.get(LEAVES) != BambooLeaves.NONE) {
                    bambooLeaves = BambooLeaves.LARGE;
                    if (blockStateDown2.isOf(this.getBambooBlock())) {
                        world.setBlockState(pos.down(), blockStateDown.with(LEAVES, BambooLeaves.SMALL), 3);
                        world.setBlockState(blockPosDown2, blockStateDown2.with(LEAVES, BambooLeaves.NONE), 3);
                    }
                }
            } else {
                bambooLeaves = BambooLeaves.SMALL;
            }
        }

        int age = state.get(AGE) != 1 && !blockStateDown2.isOf(this.getBambooBlock()) ? 0 : 1;
        int stage = (height < 11 || random.nextFloat() >= 0.25F) && height != 15 ? 0 : 1;
        world.setBlockState(pos.up(), this.getDefaultState().with(AGE, age).with(LEAVES, bambooLeaves).with(STAGE, stage), 3);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    protected int countBambooAbove(BlockView world, BlockPos pos) {
        int bambooAbove;
        for (bambooAbove = 0; bambooAbove < 16 && world.getBlockState(pos.up(bambooAbove + 1)).isOf(this.getBambooBlock()); bambooAbove++) {}

        return bambooAbove;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    protected int countBambooBelow(BlockView world, BlockPos pos) {
        int bambooBelow;
        for (bambooBelow = 0; bambooBelow < 16 && world.getBlockState(pos.down(bambooBelow + 1)).isOf(this.getBambooBlock()); bambooBelow++) {}

        return bambooBelow;
    }

    public CBambooBlock getBambooBlock() {
        return bambooBlock.get();
    }
    public CBambooSaplingBlock getSaplingBlock() {
        return saplingBlock.get();
    }
}
