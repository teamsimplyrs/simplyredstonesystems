package com.critmxbelvix.simplyrs.common.screen;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RedstoneClockScreen extends AbstractContainerScreen<RedstoneClockMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "textures/gui/redstone_clock_gui.png");
    private static final Logger LOGGER = LogManager.getLogger();
    private final int MID_X = (width - 247)/2;
    private final int MID_Y = (height - 165) / 2;

    public RedstoneClockScreen(RedstoneClockMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 247;
        this.imageHeight = 165;
        this.titleLabelX = 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = 1000;
        this.inventoryLabelY = 1000;
    }

    @Override
    protected void init() {
        super.init();

        // Add widgets and precomputed values

        this.addRenderableWidget(new ImageButton(getMidX()+20,getMidY(),17,17,0,166,17,TEXTURE,256,256,Button::onPress){
            public void onPress() {
                LOGGER.info("Image button pressed");
            }
        });
        this.addRenderableWidget(new ImageButton(getMidX()-38,getMidY(),17,17,17,166,17,TEXTURE,256,256,Button::onPress){
            public void onPress() {
                LOGGER.info("Image button pressed");
            }
        });
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - 247) / 2;
        LOGGER.info(MID_X+" "+MID_Y);
        int y = (height - 165) / 2;

        this.blit(pPoseStack, x, y, 0, 0, 248, 166);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    protected int getMidX(){
        return width/2;
    }

    protected int getMidY(){
        return  height/2;
    }
}
