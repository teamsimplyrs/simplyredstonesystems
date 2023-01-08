package com.critmxbelvix.simplyrs.common.blocks;

import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import com.critmxbelvix.simplyrs.common.items.RedstoneWrench;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static java.util.Collections.singletonList;

public class ArithmeticUnit extends Block {
    private static final Logger LOGGER = LogManager.getLogger();
    final static String name = "redstone_arithmetic_unit";
    final static CreativeModeTab tab = SimplyRSCreativeTab.SRS_TAB;
    final static Properties arithmetic_unit_properties = Properties.of(Material.STONE).strength(0.3f).dynamicShape();
    final static EnumProperty<ArithmeticModes> MODE = EnumProperty.create("mode", ArithmeticModes.class, ArithmeticModes.values());


    public ArithmeticUnit(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(MODE, ArithmeticModes.ADD)
        );
    }

    public static String m_getName()
    {
        return name;
    }
    public static CreativeModeTab m_getTab()
    {
        return tab;
    }
    public static Properties m_getProperties()
    {
        return arithmetic_unit_properties;
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canSupportRigidBlock(pLevel, pPos.below());
    }

    // Blockstates

    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return this.defaultBlockState()
                    .setValue(MODE, ArithmeticModes.ADD);
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(MODE);
    }


    // Block Drops

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {

        List<ItemStack> drops = super.getDrops(pState, pBuilder);
        if (!drops.isEmpty())
            return drops;
        return singletonList(new ItemStack(this, 1));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.getItemInHand(pHand).getItem() instanceof RedstoneWrench){
            ArithmeticModes mode = pState.getValue(MODE);
            ArithmeticModes newMode;
            newMode = switch(mode){
                case ADD -> ArithmeticModes.SUBTRACT;
                case SUBTRACT -> ArithmeticModes.MULTIPLY;
                case MULTIPLY -> ArithmeticModes.DIVIDE;
                case DIVIDE -> ArithmeticModes.ADD;
            };
            pLevel.setBlockAndUpdate(pPos,pState.setValue(MODE,newMode));
            LOGGER.info("Used");
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    enum ArithmeticModes implements StringRepresentable {
        ADD("add"),
        SUBTRACT("subtract"),
        MULTIPLY("multiply"),
        DIVIDE("divide");

        private final String name;

        ArithmeticModes(String pName){
            this.name = pName;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

}
