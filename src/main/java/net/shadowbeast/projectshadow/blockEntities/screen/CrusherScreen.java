package net.shadowbeast.projectshadow.blockEntities.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.shadowbeast.projectshadow.ProjectShadow;
import net.shadowbeast.projectshadow.blockEntities.menu.CrusherMenu;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CrusherScreen extends AbstractContainerScreen<CrusherMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ProjectShadow.MOD_ID, "textures/gui/crusher_gui.png");
    private static final int[] BUBBLELENGTHS = new int[]{29, 24, 20, 16, 11, 6, 0};
    public CrusherScreen(CrusherMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    @Override
    public void render(@NotNull GuiGraphics pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.leftPos;
        int j = this.topPos;
        pGuiGraphics.blit(TEXTURE, i, j, 0, 0,this.imageWidth, this.imageHeight);

        if (this.menu.isLit()) {
            int k = this.menu.getLitProgress();
            pGuiGraphics.blit(TEXTURE, i + 27, j + 40 - k, 176, 13 - k,14, k + 1);
        }
        int i1 = this.menu.getBrewingTicks();
        if (i1 > 0) {
            int j1 = (int)(32.0F * (1.0F - (float)i1 / 308.0F));
            if (j1 > 0) {
                pGuiGraphics.blit(TEXTURE, i + 42, j + 27, 203, 0, 37, j1);
            }
            j1 = BUBBLELENGTHS[i1 / 2 % 7];
            if (j1 > 0) {
                pGuiGraphics.blit(TEXTURE, i + 81, j + 54 - j1, 190, 19 - j1, 13, j1);
            }
        }
    }
}
