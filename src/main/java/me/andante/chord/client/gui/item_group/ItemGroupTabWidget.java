package me.andante.chord.client.gui.item_group;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.andante.chord.item.item_group.AbstractTabbedItemGroup;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ItemGroupTabWidget extends ButtonWidget {
    private final ItemGroupTab tab;
    public final Identifier texture;

    public boolean isSelected = false;

    public ItemGroupTabWidget(int x, int y, ItemGroupTab tab, PressAction onPress, Identifier texture) {
        super(x, y, 22, 22, tab.getTranslationKey(), onPress);
        this.tab = tab;
        this.texture = texture;
    }
    public ItemGroupTabWidget(int x, int y, int selectedTabIndex, AbstractTabbedItemGroup tab, CreativeInventoryScreen screen, Identifier texture) {
        this(x - 24, (y + 12) + (selectedTabIndex * 24), tab.getTabs().get(selectedTabIndex), (btn) -> {
            tab.setSelectedTabIndex(selectedTabIndex);
            MinecraftClient.getInstance().openScreen(screen);
            ((ItemGroupTabWidget) btn).isSelected = true;
        }, texture);
    }

    @Override
    protected int getYImage(boolean isHovered) {
        return isHovered || isSelected ? 1 : 0;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);

        this.drawTexture(matrices, this.x, this.y, 0, this.getYImage(this.isHovered()) * height, this.width, this.height);
        this.renderBackground(matrices, client, mouseX, mouseY);

        client.getItemRenderer().renderInGui(tab.getIcon(), this.x + 3, this.y + 3);
    }
}
