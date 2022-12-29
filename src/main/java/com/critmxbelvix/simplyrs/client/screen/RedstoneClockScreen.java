package com.critmxbelvix.simplyrs.client.screen;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.client.button.DecrementButton;
import com.critmxbelvix.simplyrs.client.button.IncrementButton;
import com.critmxbelvix.simplyrs.client.menu.RedstoneClockMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
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
    EditBox tickDelayCount;
    EditBox tickDurationCount;

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

        this.addRenderableWidget(new IncrementButton(getMidX()+30,getMidY()-48,this.menu.blockEntity.getBlockPos(),0));
        this.addRenderableWidget(new DecrementButton(getMidX()-55,getMidY()-48,this.menu.blockEntity.getBlockPos(),0));

        tickDelayCount = new EditBox(this.font, getMidX()-36, getMidY()-50, 64, 20, new TextComponent("1")) {
            {
                setValue(Integer.toString(RedstoneClockScreen.this.menu.delaySlot.get()));
                LOGGER.info(Integer.toString(RedstoneClockScreen.this.menu.blockEntity.delay));
            }
            @Override
            public void insertText(String textInput) {
                super.insertText(textInput);
                if (getValue().isEmpty())
                    setSuggestion("1");
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion("1");
                else
                    setSuggestion(null);
            }
        };

        this.addRenderableWidget(new IncrementButton(getMidX()+30,getMidY(),this.menu.blockEntity.getBlockPos(),1));
        this.addRenderableWidget(new DecrementButton(getMidX()-55,getMidY(),this.menu.blockEntity.getBlockPos(),1));

        tickDurationCount = new EditBox(this.font, getMidX()-36, getMidY()-2, 64, 20, new TextComponent("1")) {
            {
                setValue(Integer.toString(RedstoneClockScreen.this.menu.durationSlot.get()));
                LOGGER.info(Integer.toString(RedstoneClockScreen.this.menu.blockEntity.duration));
            }
            @Override
            public void insertText(String textInput) {
                super.insertText(textInput);
                if (getValue().isEmpty())
                    setSuggestion("1");
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion("1");
                else
                    setSuggestion(null);
            }
        };
        guiState.put("text:tickDelayCount", tickDelayCount);
        tickDelayCount.setMaxLength(4);
        tickDurationCount.setMaxLength(4);
        this.addWidget(this.tickDelayCount);
        this.addWidget(this.tickDurationCount);
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return tickDelayCount.isFocused() ? tickDelayCount.keyPressed(key, b, c) : super.keyPressed(key,b,c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        int currentDelayValue = Integer.parseInt(tickDelayCount.getValue());
        int currentDurationValue = Integer.parseInt(tickDurationCount.getValue());
        if(currentDelayValue != RedstoneClockScreen.this.menu.delaySlot.get()) {
            tickDelayCount.setValue(Integer.toString(RedstoneClockScreen.this.menu.delaySlot.get()));
        }
        if(currentDurationValue != RedstoneClockScreen.this.menu.durationSlot.get()) {
            tickDurationCount.setValue(Integer.toString(RedstoneClockScreen.this.menu.durationSlot.get()));
        }
        tickDelayCount.tick();
        tickDurationCount.tick();
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
        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        this.font.draw(pPoseStack, "Clock Tick Delay", 78, 20, -16777216);
        this.font.draw(pPoseStack, "Clock Tick Duration", 78, 65, -16777216);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
        tickDelayCount.render(pPoseStack, mouseX, mouseY, delta);
        tickDurationCount.render(pPoseStack,mouseX,mouseY,delta);

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
