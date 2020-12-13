package me.andante.chord.state.property;

import me.andante.chord.block.enums.TallerBlockPart;
import net.minecraft.state.property.EnumProperty;

public class CProperties {
    public static final EnumProperty<TallerBlockPart> TALLER_BLOCK_PART = EnumProperty.of("taller_block_part", TallerBlockPart.class);
}
