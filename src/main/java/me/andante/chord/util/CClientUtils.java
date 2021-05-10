package me.andante.chord.util;

import me.andante.chord.block.helper.WoodBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.BoatEntityRenderer;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class CClientUtils {
    /**
     * Run all registry helpers for a wood set.
     */
    public static void registerWoodBlocks(WoodBlocks... woodBlocks) {
        for (WoodBlocks set : woodBlocks) {
            CClientUtils.registerBoatRenderer(set);
            CClientUtils.registerRenderLayers(set);
            CClientUtils.registerLeafColors(set);
        }
    }

    /**
     * Registers a wood set's boat renderer.
     */
    public static void registerBoatRenderer(WoodBlocks set) {
        EntityRendererRegistry.INSTANCE.register(set.BOAT_ENTITY, BoatEntityRenderer::new);
    }

    /**
     * Registers a wood set's block render layers.
     */
    public static void registerRenderLayers(WoodBlocks set) {
        BlockRenderLayerMap brlmInstance = BlockRenderLayerMap.INSTANCE;
        brlmInstance.putBlocks(RenderLayer.getCutout(), set.DOOR, set.TRAPDOOR, set.SAPLING);
        brlmInstance.putBlock(set.LEAVES, RenderLayer.getCutoutMipped());
    }

    /**
     * Register a wood set's block and item leaf colors.
     */
    public static void registerLeafColors(WoodBlocks set) {
        if (set.getLeafItemColor() != -1) {
            ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null
                ? BiomeColors.getGrassColor(world, pos)
                : GrassColors.getColor(0.5D, 1.0D), set.LEAVES);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> set.getLeafItemColor(), set.LEAVES);
        }
    }
}
