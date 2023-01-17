package com.critmxbelvix.simplyrs.common.blocks.entities.RedstoneValve;

import com.critmxbelvix.simplyrs.common.blocks.RedstoneValve;
import com.critmxbelvix.simplyrs.common.registers.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class RedstoneValveEntity extends BlockEntity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private AnimationController<RedstoneValveEntity> controller = new AnimationController<RedstoneValveEntity>
            (this, "valve_controller", 5, this::predicate);
    private static final Logger LOGGER = LogManager.getLogger();
    private int current = 0;

    public RedstoneValveEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.REDSTONE_VALVE_ENTITY.get(), pPos, pBlockState);
    }

    public void use() {
    }


    /* Geckolib */

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(controller);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        int next = this.getBlockState().getValue(RedstoneValve.OUTPUT_LEVEL);
        if (next != 0) {
            controller.transitionLengthTicks = 5;
        }
        if (next == 1) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.one", ILoopType.EDefaultLoopTypes.LOOP));
        } else if (next == 2) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.two", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 3) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.three", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 4) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.four", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 5) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.five", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 6) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.six", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 7) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.seven", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 8) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.eight", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 9) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.nine", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 10) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.ten", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 11) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.eleven", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 12) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.twelve", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 13) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.thirteen", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 14) {

            controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.fourteen", ILoopType.EDefaultLoopTypes.LOOP));

        } else if (next == 15) {
            if(current==0){
                controller.transitionLengthTicks = 0;
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.fifteen", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            }
            else{
                controller.transitionLengthTicks = 5;
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.fifteen", ILoopType.EDefaultLoopTypes.LOOP));
            }

        } else if (next == 0) {
            if(current==15){
                controller.transitionLengthTicks = 0;
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.zero", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            }
            else {
                controller.transitionLengthTicks = 5;
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.redstone_valve.zero", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            }
        }
        current = next;
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
