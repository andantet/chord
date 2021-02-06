package me.andante.chord.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public abstract class AbstractCeilingPlantBlock extends PlantBlock {
    public AbstractCeilingPlantBlock(Settings settings) {
        super(settings);
    }

    abstract boolean canPlantBelow(BlockState ceiling);

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return this.canPlantBelow(world.getBlockState(pos.up()));
    }
}
