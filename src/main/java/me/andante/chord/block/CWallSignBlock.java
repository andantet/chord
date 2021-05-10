package me.andante.chord.block;

import me.andante.chord.util.CSign;
import net.minecraft.block.WallSignBlock;
import net.minecraft.util.SignType;

public class CWallSignBlock extends WallSignBlock implements CSign {
    public CWallSignBlock(Settings settings, SignType signType) {
        super(settings, signType);
    }
}
