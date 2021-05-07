package me.andante.chord.mixin;

import me.andante.chord.block.vanilla.CSign;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Inject(method = "supports", at = @At("HEAD"), cancellable = true)
    private void supports(Block block, CallbackInfoReturnable<Boolean> cir) {
        if (BlockEntityType.SIGN.equals(this) && block instanceof CSign) {
            cir.setReturnValue(true);
        }
    }
}
