package com.critmxbelvix.simplyrs.client.button;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.common.registers.PacketRegister;
import com.critmxbelvix.simplyrs.net.PacketUpdateClock;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DecrementButton extends ImageButton {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BlockPos blockpos;
    private final int type;
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "textures/gui/redstone_clock_gui.png");

    public DecrementButton(int x, int y, BlockPos pos, int type){
        super(x,y,17,17,17,166,17,TEXTURE,256,256, Button::onPress);
        this.blockpos = pos;
        this.type = type;
    }

    @Override
    public void onPress() {
        int change=-1;
        if(Screen.hasShiftDown()){
            change=-5;
        }
        else if(Screen.hasControlDown()){
            change=-10;
        }
        PacketRegister.INSTANCE.sendToServer(new PacketUpdateClock(blockpos,change,type));
        LOGGER.info("Decrement button pressed");
    }
}