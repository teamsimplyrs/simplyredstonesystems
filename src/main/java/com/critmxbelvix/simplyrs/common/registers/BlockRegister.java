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
                    LogicGateOR.m_getName(),
                    ()->new LogicGateOR(LogicGateOR.gate_or_properties.noOcclusion()),
                    LogicGateOR.m_getTab()
            );
    //Logic Gate: AND --> Registry
    public static final RegistryObject<Block> LOGICGATE_AND = registerBlock
            (
                    LogicGateAND.m_getName(),
                    ()->new LogicGateAND(LogicGateAND.gate_and_properties.noOcclusion()),
                    LogicGateAND.m_getTab()
            );
    //Logic Gate: NOR --> Registry
    public static final RegistryObject<Block> LOGICGATE_NOR = registerBlock
            (
                    LogicGateNOR.m_getName(),
                    ()->new LogicGateNOR(LogicGateNOR.gate_nor_properties.noOcclusion()),
                    LogicGateNOR.m_getTab()
            );
    //Logic Gate: NAND --> Registry
    public static final RegistryObject<Block> LOGICGATE_NAND = registerBlock
            (
                    LogicGateNAND.m_getName(),
                    ()->new LogicGateNAND(LogicGateNAND.gate_nand_properties.noOcclusion()),
                    LogicGateNAND.m_getTab()
            );
    //Logic Gate: XOR --> Registry
    public static final RegistryObject<Block> LOGICGATE_XOR = registerBlock
            (
                    LogicGateXOR.m_getName(),
                    ()-> new LogicGateXOR(LogicGateXOR.gate_xor_properties.noOcclusion()),
                    LogicGateXOR.m_getTab()
            );
    //Logic Gate: XNOR --> Registry
    public static final RegistryObject<Block> LOGICGATE_XNOR = registerBlock
            (
                    LogicGateXNOR.m_getName(),
                    ()-> new LogicGateXNOR(LogicGateXNOR.gate_xnor_properties.noOcclusion()),
                    LogicGateXNOR.m_getTab()
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
