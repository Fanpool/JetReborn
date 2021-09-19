package net.fabricmc.jetreborn.init;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

import java.util.Locale;
import java.util.function.Supplier;

public enum TRArmorMaterials implements ArmorMaterial {

    JETPACK(25, new int[]{0, 3, 0, 0}, 10, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, 0.0F, () -> Ingredient.EMPTY);

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final Lazy<Ingredient> repairMaterial;

    TRArmorMaterials(int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
                     SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterialIn) {
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.repairMaterial = new Lazy<>(repairMaterialIn);
    }

    @Override
    public int getDurability(EquipmentSlot slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getEntitySlotId()] * maxDamageFactor;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slotIn) {
        return damageReductionAmountArray[slotIn.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return soundEvent;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairMaterial.get();
    }

    @Override
    public String getName() {
        return this.toString().toLowerCase(Locale.ROOT);
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return 0; //Knockback resitance
    }
}
