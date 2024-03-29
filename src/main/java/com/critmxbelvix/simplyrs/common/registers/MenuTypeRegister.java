package com.critmxbelvix.simplyrs.common.registers;

import com.critmxbelvix.simplyrs.SimplyRedstoneSystems;
import com.critmxbelvix.simplyrs.client.menu.RedstoneArithmeticUnitMenu;
import com.critmxbelvix.simplyrs.client.menu.RedstoneClockMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeRegister {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, SimplyRedstoneSystems.MOD_ID);

    public static final RegistryObject<MenuType<RedstoneClockMenu>> REDSTONE_CLOCK_MENU =
            registerMenuType(RedstoneClockMenu::new, "redstone_clock_menu");

    public static final RegistryObject<MenuType<RedstoneArithmeticUnitMenu>> REDSTONE_ARITHMETIC_UNIT_MENU =
            registerMenuType(RedstoneArithmeticUnitMenu::new, "redstone_arithmetic_unit_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                 String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
