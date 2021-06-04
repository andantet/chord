package me.andante.chord.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(CreativeInventoryScreen.class)
public interface CreativeInventoryScreenAccessor {
    @Accessor
    static Identifier getTEXTURE() {
        throw new AssertionError();
    }
}
