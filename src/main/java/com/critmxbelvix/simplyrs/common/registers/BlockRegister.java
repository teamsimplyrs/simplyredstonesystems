package com.critmxbelvix.simplyrs.common.registers;

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
                    LogicGateOR.LOGICGATE_OR.m_getName(),
                    ()->new Block(LogicGateOR.LOGICGATE_OR.m_getProperties()),
                    LogicGateOR.LOGICGATE_OR.m_getTab()
            );
    //Logic Gate: AND --> Registry
    public static final RegistryObject<Block> LOGICGATE_AND = registerBlock
            (
                    LogicGateAND.LOGICGATE_AND.m_getName(),
                    ()->new Block(LogicGateAND.LOGICGATE_AND.m_getProperties()),
                    LogicGateAND.LOGICGATE_AND.m_getTab()
            );
    //Logic Gate: NOR --> Registry
    public static final RegistryObject<Block> LOGICGATE_NOR = registerBlock
            (
                    LogicGateNOR.LOGICGATE_NOR.m_getName(),
                    ()->new Block(LogicGateNOR.LOGICGATE_NOR.m_getProperties()),
                    LogicGateNOR.LOGICGATE_NOR.m_getTab()
            );
    //Logic Gate: NAND --> Registry
    public static final RegistryObject<Block> LOGICGATE_NAND = registerBlock
            (
                    LogicGateNAND.LOGICGATE_NAND.m_getName(),
                    ()-> new Block(LogicGateNAND.LOGICGATE_NAND.m_getProperties()),
                    LogicGateNAND.LOGICGATE_NAND.m_getTab()
            );
    //Logic Gate: XOR --> Registry
    public static final RegistryObject<Block> LOGICGATE_XOR = registerBlock
            (
                    LogicGateXOR.LOGICGATE_XOR.m_getName(),
                    ()-> new Block(LogicGateXOR.LOGICGATE_XOR.m_getProperties()),
                    LogicGateXOR.LOGICGATE_XOR.m_getTab()
            );
    //Logic Gate: XNOR --> Registry
    public static final RegistryObject<Block> LOGICGATE_XNOR = registerBlock
            (
                    LogicGateXNOR.LOGICGATE_XNOR.m_getName(),
                    ()-> new Block(LogicGateXNOR.LOGICGATE_XNOR.m_getProperties()),
                    LogicGateXNOR.LOGICGATE_XNOR.m_getTab()
            );
    //
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
