package me.andante.chord.mixin.client;

import me.andante.chord.entity.boat.CBoatEntity;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntityRenderer.class)
public class BoatEntityRendererMixin {
    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void injectCustomTextures(BoatEntity boat, CallbackInfoReturnable<Identifier> cir) {
        if (boat instanceof CBoatEntity) {
            cir.setReturnValue(((CBoatEntity) boat).getBoatTexture());
        }
    }
}
