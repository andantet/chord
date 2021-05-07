package me.andante.chord.mixin.scaffolding;

import me.andante.chord.block.vanilla.CScaffoldingBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@SuppressWarnings("unused")
@Mixin(DamageTracker.class)
public class DamageTrackerMixin {
    @Shadow @Final private LivingEntity entity;
    @Shadow @Mutable private String fallDeathSuffix;

    @Inject(method = "setFallDeathSuffix", at = @At("TAIL"))
    private void setFallDeathSuffix(CallbackInfo ci) {
        Optional<BlockPos> optional = this.entity.getClimbingPos();
        if (optional.isPresent()) {
            Block block = this.entity.world.getBlockState(optional.get()).getBlock();
            if (block instanceof CScaffoldingBlock) {
                this.fallDeathSuffix = Registry.BLOCK.getId(block).toString();
            }
        }
    }
}
