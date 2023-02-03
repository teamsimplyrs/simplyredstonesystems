package com.critmxbelvix.simplyrs.common.registers;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.common.blocks.*;
import com.critmxbelvix.simplyrs.common.blocks.RedstoneSprite;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegister {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplyRedstoneSystems.MOD_ID);

    //Register Blocks here:

    //Logic Gate: OR --> Registry
    public static final RegistryObject<Block> LOGICGATE_OR = registerBlock
            (
                    LogicGateOR.mGetName(),
                    ()->new LogicGateOR(LogicGateOR.mGetProperties().noOcclusion()),
                    LogicGateOR.mGetTab()
            );
    //Logic Gate: AND --> Registry
    public static final RegistryObject<Block> LOGICGATE_AND = registerBlock
            (
                    LogicGateAND.mGetName(),
                    ()->new LogicGateAND(LogicGateAND.mGetProperties().noOcclusion()),
                    LogicGateAND.mGetTab()
            );
    //Logic Gate: NOR --> Registry
    public static final RegistryObject<Block> LOGICGATE_NOR = registerBlock
            (
                    LogicGateNOR.mGetName(),
                    ()->new LogicGateNOR(LogicGateNOR.mGetProperties().noOcclusion()),
                    LogicGateNOR.mGetTab()
            );
    //Logic Gate: NAND --> Registry
    public static final RegistryObject<Block> LOGICGATE_NAND = registerBlock
            (
                    LogicGateNAND.mGetName(),
                    ()->new LogicGateNAND(LogicGateNAND.mGetProperties().noOcclusion()),
                    LogicGateNAND.mGetTab()
            );
    //Logic Gate: XOR --> Registry
    public static final RegistryObject<Block> LOGICGATE_XOR = registerBlock
            (
                    LogicGateXOR.mGetName(),
                    ()-> new LogicGateXOR(LogicGateXOR.mGetProperties().noOcclusion()),
                    LogicGateXOR.mGetTab()
            );
    //Logic Gate: XNOR --> Registry
    public static final RegistryObject<Block> LOGICGATE_XNOR = registerBlock
            (
                    LogicGateXNOR.mGetName(),
                    ()-> new LogicGateXNOR(LogicGateXNOR.mGetProperties().noOcclusion()),
                    LogicGateXNOR.mGetTab()
            );
    //Logic Gate: AND (2-Input) --> Registry

    public static final RegistryObject<Block> LOGICGATE_AND2 = registerBlock
            (
                    LogicGateAND2.mGetName(),
                    ()-> new LogicGateAND2(LogicGateAND2.mGetProperties().noOcclusion()),
                    LogicGateAND2.mGetTab()
            );

    //Logic Gate: NAND (2-Input) --> Registry
    public static final RegistryObject<Block> LOGICGATE_NAND2 = registerBlock
            (
                    LogicGateNAND2.mGetName(),
                    ()-> new LogicGateNAND2(LogicGateNAND2.mGetProperties().noOcclusion()),
                    LogicGateNAND2.mGetTab()
            );

    //Logic Gate: OR (2-Input) --> Registry
    public static final RegistryObject<Block> LOGICGATE_OR2 = registerBlock
            (
                    LogicGateOR2.mGetName(),
                    ()-> new LogicGateOR2(LogicGateOR2.mGetProperties().noOcclusion()),
                    LogicGateOR2.mGetTab()
            );

    //Logic Gate: NOR (2-Input) --> Registry
    public static final RegistryObject<Block> LOGICGATE_NOR2 = registerBlock
            (
                    LogicGateNOR2.mGetName(),
                    ()-> new LogicGateNOR2(LogicGateNOR2.mGetProperties().noOcclusion()),
                    LogicGateNOR2.mGetTab()
            );

    //Logic Gate: XOR (2-Input) --> Registry
    public static final RegistryObject<Block> LOGICGATE_XOR2 = registerBlock
            (
                    LogicGateXOR2.mGetName(),
                    ()-> new LogicGateXOR2(LogicGateXOR2.mGetProperties().noOcclusion()),
                    LogicGateXOR2.mGetTab()
            );

    //Logic Gate: XNOR (2-Input) --> Registry
    public static final RegistryObject<Block> LOGICGATE_XNOR2 = registerBlock
            (
                    LogicGateXNOR2.mGetName(),
                    ()-> new LogicGateXNOR2(LogicGateXNOR2.mGetProperties().noOcclusion()),
                    LogicGateXNOR2.mGetTab()
            );

    //Logic Gate: NOT (Inverter) --> Registry
    public static final RegistryObject<Block> LOGICGATE_NOT = registerBlock
            (
                    LogicGateNOT.mGetName(),
                    ()-> new LogicGateNOT(LogicGateNOT.mGetProperties().noOcclusion()),
                    LogicGateNOT.mGetTab()
            );

    //Redstone Clock --> Registry
    public static final RegistryObject<Block> REDSTONE_CLOCK = registerBlockWithoutBlockItem
            (
                    RedstoneClock.mGetName(),
                    ()-> new RedstoneClock(RedstoneClock.mGetProperties().noOcclusion())
            );

    //Redstone Crossbridge --> Registry
    public static final RegistryObject<Block> REDSTONE_CROSSBRIDGE = registerBlock
            (
                    RedstoneCrossbridge.mGetName(),
                    ()-> new RedstoneCrossbridge(RedstoneCrossbridge.mGetProperties().noOcclusion()),
                    RedstoneCrossbridge.mGetTab()
            );

    //Redstone Arithmetic Unit --> Registry
    public static final RegistryObject<Block> REDSTONE_ARITHMETIC_UNIT = registerBlock
            (
                    RedstoneArithmeticUnit.m_getName(),
                    ()-> new RedstoneArithmeticUnit(RedstoneArithmeticUnit.m_getProperties().noOcclusion()),
                    RedstoneArithmeticUnit.m_getTab()
            );

    // Redstone Valve
    public static final RegistryObject<Block> REDSTONE_VALVE = registerBlock(
            RedstoneValve.mGetName(),
            ()-> new RedstoneValve(RedstoneValve.mGetProperties().noOcclusion()),
            RedstoneValve.mGetTab()
    );

    // Piezo-Redstone Block
    public static final RegistryObject<Block> PIEZOREDSTONE_BLOCK = registerBlock(
            PiezoRedstoneBlock.mGetName(),
            ()-> new PiezoRedstoneBlock(PiezoRedstoneBlock.mGetProperties()),
            PiezoRedstoneBlock.mGetTab()
    );

    // Redstone Sprite
    public static final RegistryObject<Block> REDSTONE_SPRITE = registerBlock(
            RedstoneSprite.mGetName(),
            ()-> new RedstoneSprite(RedstoneSprite.mGetProperties()),
            RedstoneSprite.mGetTab()
    );

    // Redstone JK Flip-Flop (SR)
    public static final RegistryObject<Block> REDSTONE_JK_FLIPFLOP = registerBlock(
            JKFlipFlop.mGetName(),
            ()-> new JKFlipFlop(JKFlipFlop.mGetProperties()),
            JKFlipFlop.mGetTab()
    );

    // Redstone T Flip-Flop (SR)
    public static final RegistryObject<Block> REDSTONE_T_FLIPFLOP = registerBlock(
            TFlipFlop.mGetName(),
            ()-> new TFlipFlop(TFlipFlop.mGetProperties()),
            TFlipFlop.mGetTab()
    );

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab CreativeTab)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn,CreativeTab);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> Block, CreativeModeTab CreativeTab)
    {
        return ItemRegister.ITEMS.register(name, ()-> new BlockItem(Block.get(), new Item.Properties().tab(CreativeTab)));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }


}
