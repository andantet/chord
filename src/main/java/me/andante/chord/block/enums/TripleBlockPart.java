package me.andante.chord.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum TripleBlockPart implements StringIdentifiable {
    UPPER,
    MIDDLE,
    LOWER;

    @Override
    public String toString() {
        return this.asString();
    }

    @Override
    public String asString() {
        switch (this) {
            case UPPER:
                return "upper";
            case MIDDLE:
                return "middle";
            case LOWER:
            default:
                return "lower";
        }
    }
}
