package me.andante.chord.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum TallerBlockPart implements StringIdentifiable {
    UPPER,
    MIDDLE,
    LOWER;

    public String toString() {
        return this.asString();
    }

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
