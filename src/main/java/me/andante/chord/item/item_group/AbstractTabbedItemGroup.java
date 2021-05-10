package me.andante.chord.item.item_group;

import com.google.common.collect.Lists;
import me.andante.chord.Chord;
import me.andante.chord.client.gui.item_group.ItemGroupTab;
import me.andante.chord.item.TabbedItemGroupAppendLogic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.List;

@SuppressWarnings("unused")
public abstract class AbstractTabbedItemGroup extends ItemGroup {
    protected final Identifier id;
    private final List<ItemGroupTab> tabs = Lists.newArrayList();
    private int selectedTabIndex = 0;
    private boolean initialized = false;

    protected AbstractTabbedItemGroup(Identifier id) {
        super(AbstractTabbedItemGroup.getItemGroupIndex(), id.getNamespace() + "." + id.getPath());
        this.id = id;
    }
    protected AbstractTabbedItemGroup(String modId) {
        this(new Identifier(modId, "title"));
    }

    private static int getItemGroupIndex() {
        ((ItemGroupExtensions) ItemGroup.GROUPS[ItemGroup.GROUPS.length - 1]).fabric_expandArray();
        return ItemGroup.GROUPS.length - 1;
    }

    protected ItemGroupTab createTab(ItemConvertible item, String id) {
        return createTab(item, id, TagRegistry.item(new Identifier(Chord.MOD_ID, "creative_tabs/" + this.id.getNamespace() + "/" + id)));
    }
    protected ItemGroupTab createTab(ItemConvertible item, String id, Tag<Item> tag) {
        return createTab(new ItemStack(item), id, tag);
    }
    protected ItemGroupTab createTab(ItemStack stack, String id, Tag<Item> tag) {
        return new ItemGroupTab(stack, new Identifier(this.id.getNamespace(), id), tag);
    }

    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        for (Item item : Registry.ITEM) {
            ItemGroupTab tab = this.getSelectedItemTab();
            if (tab.matches(item)) {
                if (item instanceof TabbedItemGroupAppendLogic) {
                    ((TabbedItemGroupAppendLogic)item).appendStacksToTab(this, stacks);
                } else {
                    stacks.add(new ItemStack(item));
                }
            } else if (tab.getId().getPath().equals("all")) {
                for (ItemGroupTab i : tabs) {
                    if (i.matches(item) || item.isIn(this)) {
                        if (item instanceof TabbedItemGroupAppendLogic) {
                            ((TabbedItemGroupAppendLogic)item).appendStacksToTab(this, stacks);
                        } else {
                            stacks.add(new ItemStack(item));
                        }
                        break;
                    }
                }
            }
        }
    }

    public void init() {
        tabs.add(createAllTab());
        tabs.addAll(this.initTabs());

        initialized = true;
    }

    protected abstract List<ItemGroupTab> initTabs();

    @Environment(EnvType.CLIENT)
    public Identifier getIconBackgroundTexture() {
        return net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen.TEXTURE; // static import to prevent server-side crash;
    }

    protected ItemGroupTab createAllTab() {
        return createTab(getIcon(), "all", null);
    }

    public ItemGroupTab getSelectedItemTab() {
        return tabs.get(this.selectedTabIndex);
    }

    public List<ItemGroupTab> getTabs() {
        return this.tabs;
    }

    public int getSelectedTabIndex() {
        return this.selectedTabIndex;
    }

    public void setSelectedTabIndex(int selectedTabIndex) {
        this.selectedTabIndex = selectedTabIndex;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public Text getTranslationKey() {
        return this.selectedTabIndex != 0 ? new TranslatableText("itemGroup." + id.getNamespace(), this.getSelectedItemTab().getTranslationKey()) : super.getTranslationKey();
    }
}
