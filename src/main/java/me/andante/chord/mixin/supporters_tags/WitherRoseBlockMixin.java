package me.andante.chord.mixin.supporters_tags;

import me.andante.chord.tag.CBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.WitherRoseBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WitherRoseBlock.class)
public class WitherRoseBlockMixin {
    @Inject(method = "canPlantOnTop", at = @At("RETURN"), cancellable = true)
    private void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (floor.isIn(CBlockTags.WITHER_ROSE_SUPPORTERS)) cir.setReturnValue(true);
    }
}
