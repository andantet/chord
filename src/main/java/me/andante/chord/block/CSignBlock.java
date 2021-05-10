package me.andante.chord.block;

import me.andante.chord.util.CSign;
import net.minecraft.block.SignBlock;
import net.minecraft.util.SignType;

public class CSignBlock extends SignBlock implements CSign {
    public CSignBlock(Settings settings, SignType signType) {
        super(settings, signType);
    }
}
