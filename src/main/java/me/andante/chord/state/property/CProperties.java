package me.andante.chord.state.property;

import me.andante.chord.block.enums.TripleBlockPart;
import net.minecraft.state.property.EnumProperty;

public class CProperties {
    /**
     * A property that specifies whether a triple height block is the upper, middle, or lower half.
     */
    public static final EnumProperty<TripleBlockPart> TRIPLE_BLOCK_PART = EnumProperty.of("triple_block_part", TripleBlockPart.class);
}
