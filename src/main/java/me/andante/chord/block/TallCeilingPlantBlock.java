package me.andante.chord.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

@SuppressWarnings("unused")
public class TallCeilingPlantBlock extends AbstractTallCeilingPlantBlock {
    public TallCeilingPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantBelow(BlockState ceiling) {
        return ceiling.isOf(Blocks.GRASS_BLOCK) || ceiling.isOf(Blocks.DIRT) || ceiling.isOf(Blocks.COARSE_DIRT) || ceiling.isOf(Blocks.PODZOL) || ceiling.isOf(Blocks.FARMLAND);
    }
}
