package com.critmxbelvix.simplyrs.common.items;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RedstoneClockItemModel extends AnimatedGeoModel<RedstoneClockItem> {

    @Override
    public ResourceLocation getModelLocation(RedstoneClockItem object) {
        return new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "geo/redstone_clock.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RedstoneClockItem object) {
        return new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "textures/block/redstone_clock_texture.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RedstoneClockItem animatable) {
        return new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "animations/redstone_clock_anim.animation.json");
    }
}
