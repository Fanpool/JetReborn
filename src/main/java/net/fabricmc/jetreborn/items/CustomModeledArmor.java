package net.fabricmc.jetreborn.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface CustomModeledArmor {
    @Environment(EnvType.CLIENT)
    BipedEntityModel getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, BipedEntityModel _default);
    
    @Environment(EnvType.CLIENT)
    String getArmorTexture(String type);
}
