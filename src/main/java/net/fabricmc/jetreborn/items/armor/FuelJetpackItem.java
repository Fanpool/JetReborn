package net.fabricmc.jetreborn.items.armor;

import com.google.common.collect.Multimap;
import net.fabricmc.jetreborn.config.JetRebornConfig;
import net.fabricmc.jetreborn.items.FuelItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import reborncore.api.items.ArmorBlockEntityTicker;
import reborncore.api.items.ArmorRemoveHandler;
import reborncore.api.items.ItemStackModifiers;
import techreborn.TechReborn;
import techreborn.items.armor.TRArmourItem;

public class FuelJetpackItem extends TRArmourItem implements ItemStackModifiers, ArmorBlockEntityTicker, ArmorRemoveHandler, FuelItem {
    public final double MaxFuel = JetRebornConfig.fuelJetpackMaxFuel;
    public final double FlyCost = JetRebornConfig.fuelJetpackFlyCost;
    public final double FlyCostLow = JetRebornConfig.fuelJetpackFlyCostLow;
    public final double SpeedVert = JetRebornConfig.jetpackSpeedVert;
    public final double SpeedSide = JetRebornConfig.jetpackSpeedSide;
    public final double AccelVert = JetRebornConfig.jetpackAccelVert;
    public final double SprintSpeed = JetRebornConfig.jetpackSprintSpeed;

    public FuelJetpackItem(ArmorMaterial material) {
        super(material, EquipmentSlot.CHEST, new Item.Settings().group(TechReborn.ITEMGROUP).maxCount(1).maxDamage(-1));
    }


    @Override
    public void getAttributeModifiers(EquipmentSlot slot, ItemStack stack, Multimap<EntityAttribute, EntityAttributeModifier> attributes) {
        if (slot == this.slot && getStoredFuel(stack) > 0) {
            attributes.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(MODIFIERS[slot.getEntitySlotId()], "Armor modifier", 3, EntityAttributeModifier.Operation.ADDITION));

        }
    }

    @Override
    public void tickArmor(ItemStack stack, PlayerEntity playerEntity) {

    }

    @Override
    public void onRemoved(PlayerEntity playerEntity) {

    }


    @Override
    public double getFuelCapacity() {
        return 0;
    }
}