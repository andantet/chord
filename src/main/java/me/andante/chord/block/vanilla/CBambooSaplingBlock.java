package me.andante.chord.block.vanilla;

import net.minecraft.block.BambooBlock;
import net.minecraft.block.BambooSaplingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class CBambooSaplingBlock extends BambooSaplingBlock {
    private final Supplier<? extends CBambooBlock> bambooBlock;
    private final Supplier<? extends CBambooSaplingBlock> saplingBlock;

    public CBambooSaplingBlock(Supplier<? extends CBambooBlock> bambooBlock, Supplier<? extends CBambooSaplingBlock> saplingBlock, Settings settings) {
        super(settings);
        this.bambooBlock = bambooBlock;
        this.saplingBlock = saplingBlock;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            if (direction == Direction.UP && newState.isOf(this.getBambooBlock())) {
                world.setBlockState(pos, this.getBambooBlock().getDefaultState(), 2);
            }

            return state;
        }
    }

    @Override
    protected void grow(World world, BlockPos pos) {
        world.setBlockState(pos.up(), this.getBambooBlock().getDefaultState().with(BambooBlock.LEAVES, BambooLeaves.SMALL), 3);
    }

    public CBambooBlock getBambooBlock() {
        return bambooBlock.get();
    }
    public CBambooSaplingBlock getSaplingBlock() {
        return saplingBlock.get();
    }
}
