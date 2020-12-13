package me.andante.chord.util;

import me.andante.chord.block.helper.WoodBlocks;
import me.andante.chord.registry.SpriteIdentifierRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class CClientUtils {
    public static void registerWoodBlocks(WoodBlocks... woodBlocks) {
        for (WoodBlocks set : woodBlocks) {
            String id = set.getId();

            // sign
            SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, set.getSignTextureIdentifier()));

            id += "_sign";
            Registry.register(Registry.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.create(SignBlockEntity::new, set.SIGN, set.WALL_SIGN).build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id)));

            // boat
            EntityRendererRegistry.INSTANCE.register(set.BOAT_ENTITY, (entityRenderDispatcher, context) -> new BoatEntityRenderer(entityRenderDispatcher));

            // render layers
            BlockRenderLayerMap brlmInstance = BlockRenderLayerMap.INSTANCE;
            brlmInstance.putBlocks(RenderLayer.getCutout(), set.DOOR, set.TRAPDOOR, set.SAPLING);
            brlmInstance.putBlock(set.LEAVES, RenderLayer.getCutoutMipped());

            // color providers
            ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
                return world != null && pos != null
                    ? BiomeColors.getGrassColor(world, pos)
                    : GrassColors.getColor(0.5D, 1.0D);
            }, set.LEAVES);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> set.getLeafItemColor(), set.LEAVES);
        }
    }
}
