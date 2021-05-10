package me.andante.chord.block.enums;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum TripleBlockPart implements StringIdentifiable {
    UPPER,
    MIDDLE,
    LOWER;

    @Override
    public String asString() {
        return this.toString().toLowerCase(Locale.ROOT);
    }
}
