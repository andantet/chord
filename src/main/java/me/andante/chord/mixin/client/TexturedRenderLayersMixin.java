package me.andante.chord.mixin.client;

import me.andante.chord.util.CSignType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
    @Inject(method = "createSignTextureId", at = @At("HEAD"), cancellable = true)
    private static void replaceSignTextureId(SignType type, CallbackInfoReturnable<SpriteIdentifier> cir) {
        if (type instanceof CSignType) {
            Identifier id = Identifier.tryParse(type.getName());
            if (id != null) {
                cir.setReturnValue(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, new Identifier(id.getNamespace(), "entity/signs/" + id.getPath())));
            } else throw new NullPointerException("[CHORD] TexturedRenderLayersMixin: Sign type had an invalid identifier! " + type.getName());
        }
    }
}
