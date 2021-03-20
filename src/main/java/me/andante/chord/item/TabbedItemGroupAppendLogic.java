package me.andante.chord.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface TabbedItemGroupAppendLogic {
    void appendStacksToTab(ItemGroup group, DefaultedList<ItemStack> stacks);
}
