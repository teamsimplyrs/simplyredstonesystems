package com.critmxbelvix.simplyrs.common.registers;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import com.critmxbelvix.simplyrs.common.items.RedstoneClockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplyRedstoneSystems.MOD_ID);

    //Register Items Here:
    public static final RegistryObject<Item> REDSTONE_CLOCK_ITEM = ITEMS.register("redstone_clock",() -> new RedstoneClockItem(BlockRegister.REDSTONE_CLOCK.get(), new Item.Properties().tab(SimplyRSCreativeTab.SRS_TAB)));



    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }

}
