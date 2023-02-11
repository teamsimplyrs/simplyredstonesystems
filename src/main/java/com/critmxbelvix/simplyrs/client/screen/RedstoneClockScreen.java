package com.critmxbelvix.simplyrs.client.screen;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.client.button.DecrementButton;
import com.critmxbelvix.simplyrs.client.button.IncrementButton;
import com.critmxbelvix.simplyrs.client.button.RedstoneToggle;
import com.critmxbelvix.simplyrs.client.menu.RedstoneClockMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class RedstoneClockScreen extends AbstractContainerScreen<RedstoneClockMenu> {
    private final static HashMap<String, Object> guiState = RedstoneClockMenu.guiState;
    private final Player player;
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "textures/gui/redstone_clock_gui.png");
    private static final Logger LOGGER = LogManager.getLogger();
    private final int MID_X = (width - 247)/2;
    private final int MID_Y = (height - 165) / 2;
    private RedstoneToggle redstoneToggle;

    public RedstoneClockScreen(RedstoneClockMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 247;
        this.imageHeight = 165;
        this.titleLabelX = 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = 1000;
        this.inventoryLabelY = 1000;
        this.player = pMenu.player;
    }

    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        // Add widgets and precomputed values
        redstoneToggle = this.addRenderableWidget(new RedstoneToggle(getMidX()+80, getMidY()-70, this));

        this.addRenderableWidget(new IncrementButton(getMidX()+34,getMidY()-28,this.menu.blockEntity.getBlockPos(),0,this));
        this.addRenderableWidget(new DecrementButton(getMidX()-51,getMidY()-28,this.menu.blockEntity.getBlockPos(),0,this));

        this.addRenderableWidget(new IncrementButton(getMidX()+34,getMidY()+20,this.menu.blockEntity.getBlockPos(),1,this));
        this.addRenderableWidget(new DecrementButton(getMidX()-51,getMidY()+20,this.menu.blockEntity.getBlockPos(),1,this));
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
        int x = (width - 247) / 2;
        int y = (height - 165) / 2;
        this.blit(pPoseStack, x, y, 0, 0, 248, 166);
        RenderSystem.disableBlend();
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        Component delayText = new TextComponent("Clock Tick Delay");
        int delayWidth = this.font.width("Clock Tick Delay");
        Component durationText = new TextComponent("Clock Tick Duration");
        int durationWidth = this.font.width("Clock Tick Duration");
        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        this.font.draw(pPoseStack, delayText, this.imageWidth/2-delayWidth/2, 40, -16777216);
        this.font.draw(pPoseStack, durationText, this.imageWidth/2-durationWidth/2, 85, -16777216);
        redstoneToggle.updateStatus(this.menu.blockEntity);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        int delay = this.menu.blockEntity.delay;
        int duration = this.menu.blockEntity.duration;
        int delayWidth = this.font.width(Integer.toString(delay));
        int durationWidth = this.font.width(Integer.toString(duration));

        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        fill(pPoseStack,getMidX()-32, getMidY()-30, getMidX()-32 + 64, getMidY()-30 + 20, -16777216);
        fill(pPoseStack,getMidX()-32, getMidY()+18, getMidX()-32 + 64, getMidY()+18 + 20, -16777216);
        this.font.draw(pPoseStack, Integer.toString(delay),getMidX()-delayWidth/2,getMidY()-24,14737632);
        this.font.draw(pPoseStack, Integer.toString(duration),getMidX()-durationWidth/2,getMidY()+25,14737632);
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
