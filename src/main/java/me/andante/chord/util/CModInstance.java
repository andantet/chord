package me.andante.chord.util;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CModInstance {
    private String MOD_ID = "chord";
    private ItemGroup ITEM_GROUP;

    public CModInstance(String modId, ItemGroup itemGroup) {
        this.MOD_ID = modId;
        this.ITEM_GROUP = itemGroup;
    }
    public CModInstance(String modId) {
        this(modId, null);
    }

    // meta
    public String getModId() {
        return this.MOD_ID;
    }
    public ItemGroup getItemGroup() {
        return this.ITEM_GROUP;
    }
    public Identifier getTextureIdentifier(String path) {
        return new Identifier(this.MOD_ID, "textures/" + path + ".png");
    }

    // registries
    public Block register(String id, Block block, boolean registerItem) {
        if (registerItem) register(id, new BlockItem(block, new Item.Settings().group(this.ITEM_GROUP)));
        return Registry.register(Registry.BLOCK, new Identifier(this.MOD_ID, id), block);
    }
    public Block register(String id, Block block) {
        return this.register(id, block, true);
    }

    public Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(this.MOD_ID, id), item);
    }
    public Item register(String id) {
        return this.register(id, new Item(new Item.Settings().group(this.ITEM_GROUP)));
    }
}
