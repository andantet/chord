package me.andante.chord.block.vanilla;

import net.minecraft.block.SignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

public class CSignBlock extends SignBlock implements CSign {
    private final Identifier texture;

    public CSignBlock(Identifier texture, Settings settings) {
        super(settings, SignType.OAK);
        this.texture = texture;
    }

    @Override
    public Identifier getTexture() {
        return texture;
    }
}
