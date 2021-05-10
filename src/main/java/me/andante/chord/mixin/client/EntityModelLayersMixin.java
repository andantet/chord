package me.andante.chord.mixin.client;

import me.andante.chord.util.CSignType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(EntityModelLayers.class)
public class EntityModelLayersMixin {
    @Inject(method = "createSign", at = @At("HEAD"), cancellable = true)
    private static void createSign(SignType type, CallbackInfoReturnable<EntityModelLayer> cir) {
        if (type instanceof CSignType) {
            Identifier id = Identifier.tryParse(type.getName());
            if (id != null) {
                cir.setReturnValue(new EntityModelLayer(id, "main"));
            } else throw new NullPointerException("[CHORD] EntityModelLayersMixin: Sign type had an invalid identifier! " + type.getName());
        }
    }
}
