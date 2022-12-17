package com.critmxbelvix.simplyrs.common.blocks.entities;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RedstoneClockModel extends AnimatedGeoModel<RedstoneClockEntity> {

    @Override
    public ResourceLocation getModelLocation(RedstoneClockEntity object) {
        return new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "geo/redstone_clock.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RedstoneClockEntity object) {
        return new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "textures/block/redstone_clock_texture.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RedstoneClockEntity animatable) {
        return new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "geo/redstone_clock_anim.animation.json");
    }
}
