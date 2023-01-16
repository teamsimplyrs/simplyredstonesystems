package com.critmxbelvix.simplyrs.common.registers;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.common.creativetabs.SimplyRSCreativeTab;
import com.critmxbelvix.simplyrs.common.items.ActuatorRod;
import com.critmxbelvix.simplyrs.common.items.RedstoneClockItem;
import com.critmxbelvix.simplyrs.common.items.RedstoneWrench;
import com.critmxbelvix.simplyrs.common.items.StoneRod;
import com.critmxbelvix.simplyrs.common.items.ingots.PiezoRedstoneIngot;
import com.critmxbelvix.simplyrs.common.items.logiccores.LogicCoreAND;
import com.critmxbelvix.simplyrs.common.items.logiccores.LogicCoreNOT;
import com.critmxbelvix.simplyrs.common.items.logiccores.LogicCoreOR;
import com.critmxbelvix.simplyrs.common.items.logiccores.LogicCoreXOR;
import com.critmxbelvix.simplyrs.common.items.srsarmory.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
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

    // Logic Cores:
    public static final RegistryObject<Item> LOGICCORE_OR = ITEMS.register(
            LogicCoreOR.mGetName(),
            ()-> new LogicCoreOR(LogicCoreOR.mGetProperties())
    );

    public static final RegistryObject<Item> LOGICCORE_AND = ITEMS.register(
            LogicCoreAND.mGetName(),
            ()-> new LogicCoreAND(LogicCoreAND.mGetProperties())
    );

    public static final RegistryObject<Item> LOGICCORE_XOR = ITEMS.register(
            LogicCoreXOR.mGetName(),
            ()-> new LogicCoreXOR(LogicCoreXOR.mGetProperties())
    );

    public static final RegistryObject<Item> LOGICCORE_NOT = ITEMS.register(
            LogicCoreNOT.mGetName(),
            ()-> new LogicCoreNOT(LogicCoreNOT.mGetProperties())
    );

    // Redstone Wrench:

    public static final RegistryObject<Item> REDSTONE_WRENCH = ITEMS.register(
            RedstoneWrench.mGetName(),
            ()-> new RedstoneWrench(RedstoneWrench.mGetProperties())
    );

    // Activator Tools:

    public static final RegistryObject<Item> ACTIVATOR_AXE = ITEMS.register(
            ActivatorAxe.mGetName(),
            ()-> new ActivatorAxe()
    );
    public static final RegistryObject<Item> ACTIVATOR_HOE = ITEMS.register(
            ActivatorHoe.mGetName(),
            ()-> new ActivatorHoe()
    );
    public static final RegistryObject<Item> ACTIVATOR_PICKAXE = ITEMS.register(
            ActivatorPickaxe.mGetName(),
            ()-> new ActivatorPickaxe()
    );
    public static final RegistryObject<Item> ACTIVATOR_SHOVEL = ITEMS.register(
            ActivatorShovel.mGetName(),
            ()-> new ActivatorShovel()
    );
    public static final RegistryObject<Item> ACTIVATOR_SWORD = ITEMS.register(
            ActivatorSword.mGetName(),
            ()-> new ActivatorSword()
    );

    // Activator Armor:

    public static final RegistryObject<Item> ACTIVATOR_HELMET = ITEMS.register(
            ActivatorArmor.ActivatorHelmet.mGetName(),
            () -> new ActivatorArmor.ActivatorHelmet()
    );
    public static final RegistryObject<Item> ACTIVATOR_CHESPLATE = ITEMS.register(
            ActivatorArmor.ActivatorChestplate.mGetName(),
            () -> new ActivatorArmor.ActivatorChestplate()
    );
    public static final RegistryObject<Item> ACTIVATOR_LEGGINGS = ITEMS.register(
            ActivatorArmor.ActivatorLeggings.mGetName(),
            () -> new ActivatorArmor.ActivatorLeggings()
    );
    public static final RegistryObject<Item> ACTIVATOR_BOOTS = ITEMS.register(
            ActivatorArmor.ActivatorBoots.mGetName(),
            () -> new ActivatorArmor.ActivatorBoots()
    );

    // Piezo-Redstone Ingot:
    public static final RegistryObject<Item> PIEZOREDSTONE_INGOT = ITEMS.register(
            PiezoRedstoneIngot.mGetName(),
            ()-> new PiezoRedstoneIngot(PiezoRedstoneIngot.mGetProperties())
    );

    // Actuator Rod:
    public static final RegistryObject<Item> ACTUATOR_ROD = ITEMS.register(
            ActuatorRod.mGetName(),
            ()-> new ActuatorRod(ActuatorRod.mGetProperties())
    );

    // Stone Rod:
    public static final RegistryObject<Item> STONE_ROD = ITEMS.register(
            StoneRod.mGetName(),
            ()-> new StoneRod(StoneRod.mGetProperties())
    );

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }


}
