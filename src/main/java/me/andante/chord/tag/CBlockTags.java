package me.andante.chord.tag;

import me.andante.chord.Chord;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;

public class CBlockTags {
    /**
     * A tag that defines which blocks can support {@link PlantBlock}.
     */
    public static final Tag.Identified<Block> PLANT_SUPPORTERS = register("plant_supporters");
    /**
     * A tag that defines which blocks can support {@link WitherRoseBlock}.
     */
    public static final Tag.Identified<Block> WITHER_ROSE_SUPPORTERS = register("wither_rose_supporters");
    /**
     * A tag that defines which blocks can support {@link SproutsBlock}.
     */
    public static final Tag.Identified<Block> SPROUTS_SUPPORTERS = register("sprouts_supporters");
    /**
     * A tag that defines which blocks can support {@link RootsBlock}.
     */
    public static final Tag.Identified<Block> ROOTS_SUPPORTERS = register("roots_supporters");
    /**
     * A tag that defines which blocks can support {@link FungusBlock}.
     */
    public static final Tag.Identified<Block> FUNGUS_SUPPORTERS = register("fungus_supporters");
    /**
     * A tag that defines which blocks can support {@link StemBlock}.
     */
    public static final Tag.Identified<Block> STEM_SUPPORTERS = register("stem_supporters");
    /**
     * A tag that defines which blocks can support {@link AttachedStemBlock}.
     */
    public static final Tag.Identified<Block> ATTACHED_STEM_SUPPORTERS = register("attached_stem_supporters");
    /**
     * A tag that defines which blocks can support {@link CropBlock}.
     */
    public static final Tag.Identified<Block> CROP_SUPPORTERS = register("crop_supporters");
    /**
     * A tag that defines which blocks can support {@link DeadBushBlock}.
     */
    public static final Tag.Identified<Block> DEAD_BUSH_SUPPORTERS = register("dead_bush_supporters");
    /**
     * A tag that defines which blocks can support {@link NetherWartBlock}.
     */
    public static final Tag.Identified<Block> NETHER_WART_SUPPORTERS = register("nether_wart_supporters");
    /**
     * A tag that defines which blocks can support {@link Feature}s checking {@link Feature#isSoil(BlockState)}.
     */
    public static final Tag.Identified<Block> FEATURE_SUPPORTERS_SOIL = register("feature_supporters_soil");
    /**
     * A tag that defines which blocks can support {@link Feature}s checking {@link Feature#isStone(BlockState)}.
     */
    public static final Tag.Identified<Block> FEATURE_SUPPORTERS_STONE = register("feature_supporters_stone");

    private static Tag.Identified<Block> register(String id) {
        return TagRegistry.create(new Identifier(Chord.MOD_ID, id), BlockTags::getTagGroup); // use 'create' instead of 'block' to avoid cast
    }
}
