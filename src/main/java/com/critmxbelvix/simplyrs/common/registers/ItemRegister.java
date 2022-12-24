package com.critmxbelvix.simplyrs.common.registers;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import com.critmxbelvix.simplyrs.common.items.RedstoneClockItem;
import com.critmxbelvix.simplyrs.common.items.logiccores.LogicCoreAND;
import com.critmxbelvix.simplyrs.common.items.logiccores.LogicCoreNOT;
import com.critmxbelvix.simplyrs.common.items.logiccores.LogicCoreOR;
import com.critmxbelvix.simplyrs.common.items.logiccores.LogicCoreXOR;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplyRedstoneSystems.MOD_ID);

    //Register Items Here:
    public static final RegistryObject<Item> REDSTONE_CLOCK_ITEM = ITEMS.register(
            "redstone_clock",
            () -> new RedstoneClockItem(BlockRegister.REDSTONE_CLOCK.get(),
                    new Item.Properties().tab(SimplyRSCreativeTab.SRS_TAB))
    );

    //Logic Cores:
    public static final RegistryObject<Item> LOGICCORE_OR = ITEMS.register(
            LogicCoreOR.m_getName(),
            ()-> new LogicCoreOR(LogicCoreOR.m_getProperties())
    );

    public static final RegistryObject<Item> LOGICCORE_AND = ITEMS.register(
            LogicCoreAND.m_getName(),
            ()-> new LogicCoreAND(LogicCoreAND.m_getProperties())
    );

    public static final RegistryObject<Item> LOGICCORE_XOR = ITEMS.register(
            LogicCoreXOR.m_getName(),
            ()-> new LogicCoreXOR(LogicCoreXOR.m_getProperties())
    );

    public static final RegistryObject<Item> LOGICCORE_NOT = ITEMS.register(
            LogicCoreNOT.m_getName(),
            ()-> new LogicCoreNOT(LogicCoreNOT.m_getProperties())
    );






    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }

}
