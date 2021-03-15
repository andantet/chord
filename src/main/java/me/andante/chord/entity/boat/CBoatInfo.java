package me.andante.chord.entity.boat;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class CBoatInfo {
    private final Item item;
    private final Item planks;
    private final Identifier texture;
    private final BoatEntity.Type vanilla;

    public CBoatInfo(Item item, Item planks, Identifier texture, BoatEntity.Type vanilla) {
        this.item = item;
        this.planks = planks;
        this.texture = texture;
        this.vanilla = vanilla;
    }

    public Item asItem() {
        return item;
    }

    public Item asPlanks() {
        return planks;
    }

    public Identifier getTexture() {
        return texture;
    }

    public BoatEntity.Type getVanillaType() {
        return vanilla;
    }
}
