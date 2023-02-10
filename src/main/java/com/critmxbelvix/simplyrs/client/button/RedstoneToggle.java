package com.critmxbelvix.simplyrs.client.button;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.client.screen.RedstoneClockScreen;
import com.critmxbelvix.simplyrs.common.blocks.entities.RedstoneClock.RedstoneClockEntity;
import com.critmxbelvix.simplyrs.common.registers.PacketRegister;
import com.critmxbelvix.simplyrs.net.PacketUpdateClock;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RedstoneToggle extends Button {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ResourceLocation resourceLocation;
    private final int xTexStart;
    private final int yTexStart;
    private final int yDiffTex;
    private final int textureWidth;
    private final int textureHeight;
    private final RedstoneClockScreen screen;
    private boolean redstone_needed;


    public RedstoneToggle(int pX, int pY, RedstoneClockScreen screen) {
        super(pX, pY, 17, 17, TextComponent.EMPTY, Button::onPress);
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.xTexStart = 34;
        this.yTexStart = 166;
        this.yDiffTex = 17;
        this.resourceLocation = new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "textures/gui/redstone_clock_gui.png");
        this.screen = screen;
        redstone_needed = screen.getMenu().blockEntity.redstone_needed;
    }

    @Override
    public void onPress() {
        PacketRegister.INSTANCE.sendToServer(new PacketUpdateClock(screen.getMenu().blockEntity.getBlockPos(),0,2));
    }

    public void updateStatus(RedstoneClockEntity entity){
        this.redstone_needed = entity.redstone_needed;
    }

    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resourceLocation);
        int i = this.yTexStart;
        if (this.redstone_needed) {
            i += this.yDiffTex;
        }

        RenderSystem.enableDepthTest();
        blit(pPoseStack, this.x, this.y, (float)this.xTexStart, (float)i, this.width, this.height, this.textureWidth, this.textureHeight);
        if (this.isHovered) {
            this.renderToolTip(pPoseStack, pMouseX, pMouseY);
        }
    }

}
