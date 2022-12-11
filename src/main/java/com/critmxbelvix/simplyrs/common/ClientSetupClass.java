package com.critmxbelvix.simplyrs.common;

import com.critmxbelvix.simplyrs.common.registers.BlockRegister;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class ClientSetupClass {

    public static void renderAllTranslucent()
    {
        ItemBlockRenderTypes.setRenderLayer(BlockRegister.LOGICGATE_AND.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegister.LOGICGATE_NAND.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegister.LOGICGATE_OR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegister.LOGICGATE_NOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegister.LOGICGATE_XOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegister.LOGICGATE_XNOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegister.LOGICGATE_AND2.get(), RenderType.translucent());
    }


}
