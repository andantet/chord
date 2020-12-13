package me.andante.chord.tag;

import me.andante.chord.Chord;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class CBlockTags {
    public static final Tag<Block> PLANT_SUPPORTERS = register("plant_supporters");

    public CBlockTags() {}

    private static Tag<Block> register(String id) {
        return TagRegistry.block(new Identifier(Chord.MOD_ID, id));
    }
}
