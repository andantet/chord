package me.andante.chord.client.gui.itemgroup;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class ItemGroupTab {
    private final Tag<Item> tag;
    private final ItemStack icon;
    private final Identifier id;

    public ItemGroupTab(ItemStack icon, Identifier id, Tag<Item> tag) {
        this.tag = tag;
        this.icon = icon;
        this.id = id;
    }

    public Identifier getId() {
        return this.id;
    }

    public TranslatableText getTranslationKey() {
        return new TranslatableText("itemGroup.tab." + id);
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public boolean matches(Item item) {
        return tag != null && tag.contains(item);
    }

    public ItemGroupTabWidget createWidget(int x, int y, int selectedTabIndex, AbstractTabbedItemGroup tab, CreativeInventoryScreen screen) {
        return new ItemGroupTabWidget(x, y, selectedTabIndex, tab, screen);
    }
}
