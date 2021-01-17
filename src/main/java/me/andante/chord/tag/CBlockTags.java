package me.andante.chord.tag;

import me.andante.chord.Chord;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class CBlockTags {
    /**
     * A tag that defines which blocks can support {@link net.minecraft.block.PlantBlock}.
     */
    public static final Tag<Block> PLANT_SUPPORTERS = register("plant_supporters");
    /**
     * A tag that defines which blocks can support {@link net.minecraft.block.WitherRoseBlock}.
     */
    public static final Tag<Block> WITHER_ROSE_SUPPORTERS = register("wither_rose_supporters");
    /**
     * A tag that defines which blocks can support {@link net.minecraft.block.SproutsBlock}.
     */
    public static final Tag<Block> SPROUTS_SUPPORTERS = register("sprouts_supporters");
    /**
     * A tag that defines which blocks can support {@link net.minecraft.block.RootsBlock}.
     */
    public static final Tag<Block> ROOTS_SUPPORTERS = register("roots_supporters");
    /**
     * A tag that defines which blocks can support {@link net.minecraft.block.FungusBlock}.
     */
    public static final Tag<Block> FUNGUS_SUPPORTERS = register("fungus_supporters");

    private static Tag<Block> register(String id) {
        return TagRegistry.block(new Identifier(Chord.MOD_ID, id));
    }
}
