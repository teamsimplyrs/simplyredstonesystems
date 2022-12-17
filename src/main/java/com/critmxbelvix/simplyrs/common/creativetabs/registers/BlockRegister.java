package com.critmxbelvix.simplyrs.common.creativetabs.registers;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.common.blocks.*;
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
                    LogicGateOR.m_getName(),
                    ()->new LogicGateOR(LogicGateOR.m_getProperties().noOcclusion()),
                    LogicGateOR.m_getTab()
            );
    //Logic Gate: AND --> Registry
    public static final RegistryObject<Block> LOGICGATE_AND = registerBlock
            (
                    LogicGateAND.m_getName(),
                    ()->new LogicGateAND(LogicGateAND.m_getProperties().noOcclusion()),
                    LogicGateAND.m_getTab()
            );
    //Logic Gate: NOR --> Registry
    public static final RegistryObject<Block> LOGICGATE_NOR = registerBlock
            (
                    LogicGateNOR.m_getName(),
                    ()->new LogicGateNOR(LogicGateNOR.m_getProperties().noOcclusion()),
                    LogicGateNOR.m_getTab()
            );
    //Logic Gate: NAND --> Registry
    public static final RegistryObject<Block> LOGICGATE_NAND = registerBlock
            (
                    LogicGateNAND.m_getName(),
                    ()->new LogicGateNAND(LogicGateNAND.m_getProperties().noOcclusion()),
                    LogicGateNAND.m_getTab()
            );
    //Logic Gate: XOR --> Registry
    public static final RegistryObject<Block> LOGICGATE_XOR = registerBlock
            (
                    LogicGateXOR.m_getName(),
                    ()-> new LogicGateXOR(LogicGateXOR.m_getProperties().noOcclusion()),
                    LogicGateXOR.m_getTab()
            );
    //Logic Gate: XNOR --> Registry
    public static final RegistryObject<Block> LOGICGATE_XNOR = registerBlock
            (
                    LogicGateXNOR.m_getName(),
                    ()-> new LogicGateXNOR(LogicGateXNOR.m_getProperties().noOcclusion()),
                    LogicGateXNOR.m_getTab()
            );
    //Logic Gate: AND (2-Input) --> Registry

    public static final RegistryObject<Block> LOGICGATE_AND2 = registerBlock
            (
                    LogicGateAND2.m_getName(),
                    ()-> new LogicGateAND2(LogicGateAND2.m_getProperties().noOcclusion()),
                    LogicGateAND2.m_getTab()
            );

    //Logic Gate: NAND (2-Input) --> Registry
    public static final RegistryObject<Block> LOGICGATE_NAND2 = registerBlock
            (
                    LogicGateNAND2.m_getName(),
                    ()-> new LogicGateNAND2(LogicGateNAND2.m_getProperties().noOcclusion()),
                    LogicGateNAND2.m_getTab()
            );

    //Logic Gate: OR (2-Input) --> Registry
    public static final RegistryObject<Block> LOGICGATE_OR2 = registerBlock
            (
                    LogicGateOR2.m_getName(),
                    ()-> new LogicGateOR2(LogicGateOR2.m_getProperties().noOcclusion()),
                    LogicGateOR2.m_getTab()
            );

    //Logic Gate: NOR (2-Input) --> Registry
    public static final RegistryObject<Block> LOGICGATE_NOR2 = registerBlock
            (
                    LogicGateNOR2.m_getName(),
                    ()-> new LogicGateNOR2(LogicGateNOR2.m_getProperties().noOcclusion()),
                    LogicGateNOR2.m_getTab()
            );

    //Logic Gate: XOR (2-Input) --> Registry
    public static final RegistryObject<Block> LOGICGATE_XOR2 = registerBlock
            (
                    LogicGateXOR2.m_getName(),
                    ()-> new LogicGateXOR2(LogicGateXOR2.m_getProperties().noOcclusion()),
                    LogicGateXOR2.m_getTab()
            );

    //Logic Gate: XNOR (2-Input) --> Registry
    public static final RegistryObject<Block> LOGICGATE_XNOR2 = registerBlock
            (
                    LogicGateXNOR2.m_getName(),
                    ()-> new LogicGateXNOR2(LogicGateXNOR2.m_getProperties().noOcclusion()),
                    LogicGateXNOR2.m_getTab()
            );

    //Logic Gate: NOT (Inverter) --> Registry
    public static final RegistryObject<Block> LOGICGATE_NOT = registerBlock
            (
                    LogicGateNOT.m_getName(),
                    ()-> new LogicGateNOT(LogicGateNOT.m_getProperties().noOcclusion()),
                    LogicGateNOT.m_getTab()
            );

    //Clock Block --> Registry
    public static final RegistryObject<Block> REDSTONE_CLOCK = registerBlockWithoutBlockItem
            (
                    RedstoneClock.m_getName(),
                    ()-> new RedstoneClock(RedstoneClock.m_getProperties().noOcclusion())
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
