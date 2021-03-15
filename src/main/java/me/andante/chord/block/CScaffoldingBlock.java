package me.andante.chord.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

/**
 * Modified version of vanilla's {@link ScaffoldingBlock} that supports non-vanilla scaffolding block definitions
 * @see ScaffoldingBlock
 */
public class CScaffoldingBlock extends ScaffoldingBlock {
    public CScaffoldingBlock(Settings settings) {
        super(settings);
    }

    /**
     * Calculates the distance away from the root scaffolding block, utilising instanceof as opposed to {@link AbstractBlockState#isOf(Block)}
     * @see ScaffoldingBlock#calculateDistance(BlockView, BlockPos)
     */
    public static int calculateDistance(BlockView world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy().move(Direction.DOWN);
        BlockState blockState = world.getBlockState(mutable);
        int distance = 7;
        if (blockState.getBlock() instanceof ScaffoldingBlock) {
            distance = blockState.get(DISTANCE);
        } else if (blockState.isSideSolidFullSquare(world, mutable, Direction.UP)) {
            return 0;
        }

        for (Direction iDirection : Direction.Type.HORIZONTAL) {
            BlockState iState = world.getBlockState(mutable.set(pos, iDirection));
            if (iState.getBlock() instanceof ScaffoldingBlock) {
                distance = Math.min(distance, iState.get(DISTANCE) + 1);
                if (distance == 1) {
                    break;
                }
            }
        }

        return distance;
    }

    /**
     * Modified version of {@link ScaffoldingBlock#getPlacementState(ItemPlacementContext)} to use {@link CScaffoldingBlock#calculateDistance(BlockView, BlockPos)}
     * @see ScaffoldingBlock#getPlacementState(ItemPlacementContext)
     */
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        int distance = calculateDistance(world, blockPos); // modified

        return this.getDefaultState().with(WATERLOGGED, world.getFluidState(blockPos).getFluid() == Fluids.WATER).with(DISTANCE, distance).with(BOTTOM, this.shouldBeBottom(world, blockPos, distance));
    }

    /**
     * Modified version of {@link ScaffoldingBlock#scheduledTick(BlockState, ServerWorld, BlockPos, Random)} to use {@link CScaffoldingBlock#calculateDistance(BlockView, BlockPos)}
     * @see ScaffoldingBlock#scheduledTick(BlockState, ServerWorld, BlockPos, Random)
     */
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int distance = calculateDistance(world, pos); // modified
        BlockState blockState = state.with(DISTANCE, distance).with(BOTTOM, this.shouldBeBottom(world, pos, distance));
        if (blockState.get(DISTANCE) == 7) {
            if (state.get(DISTANCE) == 7) {
                world.spawnEntity(new FallingBlockEntity(world, (double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, blockState.with(WATERLOGGED, false)));
            } else {
                world.breakBlock(pos, true);
            }
        } else if (state != blockState) {
            world.setBlockState(pos, blockState, 3);
        }
    }

    /**
     * Modified version of {@link ScaffoldingBlock#canPlaceAt(BlockState, WorldView, BlockPos)} to use {@link CScaffoldingBlock#calculateDistance(BlockView, BlockPos)}
     * @see ScaffoldingBlock#canPlaceAt(BlockState, WorldView, BlockPos)
     */
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return calculateDistance(world, pos) < 7;
    }

    /**
     * Checks if the scaffolding block is the root, utilising instanceof as opposed to {@link AbstractBlockState#isOf(Block)}
     */
    private boolean shouldBeBottom(BlockView world, BlockPos pos, int distance) {
        return distance > 0 && !(world.getBlockState(pos.down()).getBlock() instanceof ScaffoldingBlock);
    }
}
