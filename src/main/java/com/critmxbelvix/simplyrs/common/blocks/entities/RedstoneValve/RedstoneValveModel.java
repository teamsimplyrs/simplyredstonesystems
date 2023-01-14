package com.critmxbelvix.simplyrs.common.blocks.entities.RedstoneValve;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RedstoneValveModel extends AnimatedGeoModel<RedstoneValveEntity> {

    @Override
    public ResourceLocation getModelLocation(RedstoneValveEntity object) {
        return new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "geo/redstone_valve.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RedstoneValveEntity object) {
        return new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "textures/block/redstone_valve.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RedstoneValveEntity animatable) {
        return new ResourceLocation(SimplyRedstoneSystems.MOD_ID, "animations/redstone_valve.animation.json");
    }
}