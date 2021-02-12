package me.andante.chord.mixin.client;

import me.andante.chord.block.CSign;
import net.minecraft.block.*;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.util.SignType;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mixin(SignBlockEntityRenderer.class)
public class SignBlockEntityRendererMixin {
    @Shadow @Final
    private TextRenderer textRenderer;
    @Shadow @Final
    private Map<SignType, SignBlockEntityRenderer.SignModel> typeToModel;

    /*
     * Code copied from the default SignBlockEntityRenderer#render.
     * There's probably a more elegant solution.
     */
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void injectCustomTexture(SignBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider consumers, int light, int overlay, CallbackInfo ci) {
        Block block = Objects.requireNonNull(blockEntity.getWorld()).getBlockState(blockEntity.getPos()).getBlock();
        if (block instanceof CSign) {
            //
            // START DEFAULT
            //

            BlockState blockState = blockEntity.getCachedState();
            matrices.push();
            SignType signType = SignBlockEntityRenderer.getSignType(blockState.getBlock());
            SignBlockEntityRenderer.SignModel signModel = this.typeToModel.get(signType);
            float rotation;
            if (blockState.getBlock() instanceof SignBlock) {
                matrices.translate(0.5D, 0.5D, 0.5D);
                rotation = -((float)(blockState.get(SignBlock.ROTATION) * 360) / 16.0F);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(rotation));
                signModel.stick.visible = true;
            } else {
                matrices.translate(0.5D, 0.5D, 0.5D);
                rotation = -blockState.get(WallSignBlock.FACING).asRotation();
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(rotation));
                matrices.translate(0.0D, -0.3125D, -0.4375D);
                signModel.stick.visible = false;
            }

            matrices.push();
            matrices.scale(0.6666667F, -0.6666667F, -0.6666667F);
            SpriteIdentifier spriteIdentifier = new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ((CSign) block).getTexture()); // MODIFIED LINE
            VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(consumers, signModel::getLayer);
            signModel.root.render(matrices, vertexConsumer, light, overlay);
            matrices.pop();
            matrices.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
            matrices.scale(0.010416667F, -0.010416667F, 0.010416667F);
            int signColor = blockEntity.getTextColor().getSignColor();
            int blue = (int)((double) NativeImage.getRed(signColor) * 0.4D);
            int green = (int)((double)NativeImage.getGreen(signColor) * 0.4D);
            int red = (int)((double)NativeImage.getBlue(signColor) * 0.4D);
            int color = NativeImage.getAbgrColor(0, red, green, blue);

            for(int s = 0; s < 4; ++s) {
                OrderedText orderedText = blockEntity.getTextBeingEditedOnRow(s, (text) -> {
                    List<OrderedText> list = this.textRenderer.wrapLines(text, 90);
                    return list.isEmpty() ? OrderedText.EMPTY : list.get(0);
                });
                if (orderedText != null) {
                    float t = (float)(-this.textRenderer.getWidth(orderedText) / 2);
                    int u = blockState.get(AbstractSignBlock.LIT) ? 15728880 : light;
                    this.textRenderer.draw(orderedText, t, (float)(s * 10 - 20), color, false, matrices.peek().getModel(), consumers, false, 0, u);
                }
            }

            matrices.pop();

            //
            // END DEFAULT
            //

            ci.cancel();
        }
    }
}
