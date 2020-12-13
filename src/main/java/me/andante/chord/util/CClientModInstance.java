package me.andante.chord.util;

import me.andante.chord.block.helper.WoodBlocks;
import me.andante.chord.registry.SpriteIdentifierRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class CClientModInstance {
    public static void registerSignRenderers(WoodBlocks... woodBlocks) {
        for (WoodBlocks set : woodBlocks) {
            String id = set.getId();

            SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, set.getSignTextureIdentifier()));

            id += "_sign";
            Registry.register(Registry.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.create(SignBlockEntity::new, set.SIGN, set.WALL_SIGN).build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id)));
        }
    }
    public static void registerBoatRenderers(EntityType<?>... boats) {
        for (EntityType<?> boat : boats) {
            EntityRendererRegistry.INSTANCE.register(boat, (entityRenderDispatcher, context) -> new BoatEntityRenderer(entityRenderDispatcher));
        }
    }
}
