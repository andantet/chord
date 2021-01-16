package me.andante.chord.item;

import me.andante.chord.block.CScaffoldingBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ScaffoldingItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Modified version of vanilla's {@link ScaffoldingItem} that supports non-vanilla scaffolding block definitions
 */
@SuppressWarnings("unused")
public class CScaffoldingItem extends ScaffoldingItem {
    public CScaffoldingItem(Block block, Settings settings) {
        super(block, settings);
    }

    /**
     * @see ScaffoldingItem#getPlacementContext(ItemPlacementContext)
     */
    @Override
    public ItemPlacementContext getPlacementContext(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();

        if (!world.getBlockState(blockPos).isOf(this.getBlock())) {
            return CScaffoldingBlock.calculateDistance(world, blockPos) == 7 ? null : ctx; // run modified method that checks instanceof
        } else {
            return super.getPlacementContext(ctx);
        }
    }
}
