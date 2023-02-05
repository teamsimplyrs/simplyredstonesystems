package com.critmxbelvix.simplyrs.client.screen;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.client.menu.RedstoneArithmeticUnitMenu;
import com.critmxbelvix.simplyrs.client.menu.RedstoneClockMenu;
import com.critmxbelvix.simplyrs.common.blocks.RedstoneArithmeticUnit;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class RedstoneArithmeticUnitScreen extends AbstractContainerScreen<RedstoneArithmeticUnitMenu> {
    private final static HashMap<String, Object> guiState = RedstoneClockMenu.guiState;
    private final Player player;
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "textures/gui/redstone_arithmetic_gui.png");
    private static final Logger LOGGER = LogManager.getLogger();
    private final int MID_X = (width - 247)/2;
    private final int MID_Y = (height - 165) / 2;

    public RedstoneArithmeticUnitScreen(RedstoneArithmeticUnitMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.titleLabelX = 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = 1000;
        this.inventoryLabelY = 1000;
        this.player = pMenu.player;
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = 176;
        this.imageHeight = 127;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        // Add widgets and precomputed values
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return false;
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }
    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(pPoseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        int operation = this.menu.blockEntity.getBlockState().getValue(RedstoneArithmeticUnit.MODE).ordinal();
        switch(operation){
            case 0:
                this.blit(pPoseStack,this.leftPos+42,this.topPos+59,72,127,18,18);
                this.blit(pPoseStack,this.leftPos+78,this.topPos+59,72,127,18,18);
                break;
            case 1:
                this.blit(pPoseStack,this.leftPos+42,this.topPos+59,72,145,18,18);
                this.blit(pPoseStack,this.leftPos+78,this.topPos+59,72,145,18,18);
                break;
            case 2:
                this.blit(pPoseStack,this.leftPos+42,this.topPos+59,72,163,18,18);
                this.blit(pPoseStack,this.leftPos+78,this.topPos+59,72,163,18,18);
                break;
            case 3:
                this.blit(pPoseStack,this.leftPos+42,this.topPos+59,72,181,18,18);
                this.blit(pPoseStack,this.leftPos+78,this.topPos+59,72,181,18,18);
                break;
        }
        RenderSystem.disableBlend();
    }


    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        this.font.draw(pPoseStack, "a",30,64,0);
        this.font.draw(pPoseStack, "b",66,64,0);
        this.font.draw(pPoseStack, "c",102,64,0);
        this.font.draw(pPoseStack, "a",30,94,0);
        this.font.draw(pPoseStack, "b",66,94,0);
        this.font.draw(pPoseStack, "c",102,94,0);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }


    protected int getMidX(){
        return width/2;
    }

    protected int getMidY(){
        return  height/2;
    }
}
