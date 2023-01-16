package com.critmxbelvix.simplyrs.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;


public class ModTags {
    public static class Items {
        public static final TagKey<Item> STONE_ROD = forgeTag("rods/stone");

        private static TagKey<Item> forgeTag(String name){
            return ItemTags.create(new ResourceLocation("forge",name));
        }
    }
}
