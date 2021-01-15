package me.andante.chord.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@SuppressWarnings({"unused","deprecation"})
public class BeamBlock extends Block implements Waterloggable {
    public static final BooleanProperty POST = BooleanProperty.of("post");
    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty EAST = Properties.EAST;
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty WEST = Properties.WEST;
    public static final BooleanProperty UP = Properties.UP;
    public static final BooleanProperty DOWN = Properties.DOWN;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static final Map<Direction, BooleanProperty> PROP_MAP = Util.make(new HashMap<>(),
        map -> {
            map.put(Direction.NORTH, NORTH);
            map.put(Direction.EAST, EAST);
            map.put(Direction.SOUTH, SOUTH);
            map.put(Direction.WEST, WEST);
            map.put(Direction.UP, UP);
            map.put(Direction.DOWN, DOWN);
        });

    private final ShapeUtil shapeUtil;

    public BeamBlock(AbstractBlock.Settings settings) {
        super(settings);

        this.setDefaultState(this.getStateManager().getDefaultState().with(POST, true).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false).with(WATERLOGGED, false));
        this.shapeUtil = new ShapeUtil(this);
    }

    public Property<Boolean> getProperty(Direction facing) {
        return PROP_MAP.get(facing);
    }

    private BlockState makeConnections(World world, BlockPos pos) {
        Boolean north = this.isConnectable(world, pos.north(), Direction.SOUTH);
        Boolean east = this.isConnectable(world, pos.east(), Direction.WEST);
        Boolean south = this.isConnectable(world, pos.south(), Direction.NORTH);
        Boolean west = this.isConnectable(world, pos.west(), Direction.EAST);
        Boolean up = this.isConnectable(world, pos.up(), Direction.DOWN);
        Boolean down = this.isConnectable(world, pos.down(), Direction.UP);

        return this.getDefaultState().with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west).with(UP, up).with(DOWN, down);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return state.with(getProperty(direction), this.isConnectable(world, posFrom, direction.getOpposite()));
    }

    protected boolean isConnectable(WorldAccess world, BlockPos pos, Direction dir) {
        return world.getBlockState(pos).getBlock() instanceof BeamBlock;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POST, NORTH, EAST, SOUTH, WEST, UP, DOWN, WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.makeConnections(ctx.getWorld(), ctx.getBlockPos()).with(POST, ctx.getSide() == Direction.UP || ctx.getSide() == Direction.DOWN);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.shapeUtil.getShape(state);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    /**
     * <p>
     * Copyright (c) 2021 TechReborn
     * <p>
     * Permission is hereby granted, free of charge, to any person obtaining a copy
     * of this software and associated documentation files (the "Software"), to deal
     * in the Software without restriction, including without limitation the rights
     * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     * copies of the Software, and to permit persons to whom the Software is
     * furnished to do so, subject to the following conditions:
     * <p>
     * The above copyright notice and this permission notice shall be included in
     * all copies or substantial portions of the Software.
     * <p>
     * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
     * SOFTWARE.
     */
    public static final class ShapeUtil {
        private final BeamBlock beamBlock;
        private final HashMap<BlockState, VoxelShape> shapes;

        public ShapeUtil(BeamBlock beamBlock) {
            this.beamBlock = beamBlock;
            this.shapes = createStateShapeMap();
        }

        private HashMap<BlockState, VoxelShape> createStateShapeMap() {
            return Util.make(
                new HashMap<>(),
                map -> beamBlock.getStateManager().getStates().forEach(
                    state -> map.put(state, getStateShape(state))
                )
            );
        }

        private VoxelShape getStateShape(BlockState state) {
            final double size = 4;
            final VoxelShape baseShape = !state.get(POST)
                    ? Block.createCuboidShape(size, size, size, 16.0D - size, 16.0D - size, 16.0D - size)
                    : Block.createCuboidShape(size, 0.0D, size, 16.0D - size, 16.0D, 16.0D - size);

            final List<VoxelShape> connections = new ArrayList<>();
            for (Direction dir : Direction.values()) {
                if (state.get(PROP_MAP.get(dir))) {
                    double x = dir == Direction.WEST ? 0 : dir == Direction.EAST ? 16D : size;
                    double z = dir == Direction.NORTH ? 0 : dir == Direction.SOUTH ? 16D : size;
                    double y = dir == Direction.DOWN ? 0 : dir == Direction.UP ? 16D : size;

                    VoxelShape shape = Block.createCuboidShape(x, y, z, 16.0D - size, 16.0D - size, 16.0D - size);
                    connections.add(shape);
                }
            }
            return VoxelShapes.union(baseShape, connections.toArray(new VoxelShape[] {}));
        }

        public VoxelShape getShape(BlockState state) {
            return shapes.get(state);
        }
    }
}
