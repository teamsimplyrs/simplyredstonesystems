package com.critmxbelvix.simplyrs.client.button;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.client.screen.RedstoneArithmeticUnitScreen;
import com.critmxbelvix.simplyrs.common.registers.PacketRegister;
import com.critmxbelvix.simplyrs.net.PacketUpdateArithmeticUnit;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TriStateOperandButton extends Button{
    private static final Logger LOGGER = LogManager.getLogger();
    private final ResourceLocation resourceLocation;
    private final int xTexStart;
    private final int yTexStart;
    private final int yDiffTex;
    private final int textureWidth;
    private final int textureHeight;
    private final List<Component> tooltips = new ArrayList<>();
    private final RedstoneArithmeticUnitScreen screen;

    private final int operand;
    private final int type;

    public TriStateOperandButton(int pX, int pY,int type, int operand, RedstoneArithmeticUnitScreen screen) {
        super(pX, pY, 18, 18, TextComponent.EMPTY, Button::onPress);
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.xTexStart = 0;
        this.yTexStart = 127;
        this.yDiffTex = 18;
        this.resourceLocation = new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "textures/gui/redstone_arithmetic_gui.png");
        this.screen = screen;
        this.operand = operand;
        this.type = type;
    }

    @Override
    public void onPress() {
        if(type == 0){
            screen.currentOperand = operand;
            LOGGER.info(screen.currentOperand);
        }
        else{
            PacketRegister.INSTANCE.sendToServer(new PacketUpdateArithmeticUnit(screen.getMenu().blockEntity.getBlockPos(),operand,screen.currentOperand));
        }
    }

    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resourceLocation);
        int i = this.yTexStart;

        if (operand == screen.currentOperand && type==0) {
            i+= 2* this.yDiffTex;
            this.renderToolTip(pPoseStack, pMouseX, pMouseY);
        }
        if(type==1 && this.isHovered){
            i += 2* this.yDiffTex;
        }

        RenderSystem.enableDepthTest();
        blit(pPoseStack, this.x, this.y, (float)this.xTexStart, (float)i, this.width, this.height, this.textureWidth, this.textureHeight);

    }

    @Override
    public void renderToolTip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        screen.renderComponentTooltip(pPoseStack,tooltips,pMouseX,pMouseY);
    }
}
