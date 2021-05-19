package me.andante.chord.mixin.scaffolding;

import me.andante.chord.block.CScaffoldingBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "applyClimbingSpeed", at = @At("RETURN"), cancellable = true)
    private void applyClimbingSpeed(Vec3d motion, CallbackInfoReturnable<Vec3d> cir) {
        LivingEntity $this = LivingEntity.class.cast(this);
        Block block = $this.world.getBlockState($this.getBlockPos()).getBlock();
        if ($this.isClimbing() && block instanceof CScaffoldingBlock && $this.isHoldingOntoLadder()) {
            cir.setReturnValue(cir.getReturnValue().add(0.0D, -0.15000000596046448D, 0.0D));
        }
    }
}
