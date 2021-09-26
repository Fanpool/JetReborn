package net.fabricmc.jetreborn.items.armor;

import com.google.common.collect.Multimap;
import net.fabricmc.jetreborn.config.JetRebornConfig;
import net.fabricmc.jetreborn.handler.InputHandler;
import net.fabricmc.jetreborn.items.FuelItem;
import net.fabricmc.jetreborn.items.Jetpack;
import net.fabricmc.jetreborn.mixin.ServerPlayNetworkHandlerAccessor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import reborncore.api.items.ArmorBlockEntityTicker;
import reborncore.api.items.ArmorRemoveHandler;
import reborncore.api.items.ItemStackModifiers;
import techreborn.TechReborn;
import techreborn.items.armor.TRArmourItem;

public class FuelJetpackItem extends TRArmourItem implements ItemStackModifiers, ArmorBlockEntityTicker, ArmorRemoveHandler, FuelItem, Jetpack {

    public final double maxFuel = JetRebornConfig.fuelJetpackMaxFuel;
    public final double flyCost = JetRebornConfig.fuelJetpackFlyCost;
    public final double flyCostSlow = JetRebornConfig.fuelJetpackFlyCostLow;
    public final double speedVert = JetRebornConfig.fuelJetpackSpeedVert;
    public final double speedSide = JetRebornConfig.fuelJetpackSpeedSide;
    public final double accelVert = JetRebornConfig.fuelJetpackAccelVert;
    public final double sprintSpeed = JetRebornConfig.fuelJetpackSprintSpeed;
    public final double speedHover = JetRebornConfig.fuelJetpackSpeedHover;
    public final double speedHoverSlow = JetRebornConfig.fuelJetpackSpeedHoverSlow;
    public final boolean enableFlight = JetRebornConfig.jetpackEnableFlight;

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
    public void tickArmor(ItemStack stack, PlayerEntity player) {
        if (enableFlight) {
            ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
            Item item = chest.getItem();
            if (!chest.isEmpty() && item instanceof ElectricJetpackItem) {
                ElectricJetpackItem jetpack;
                jetpack = (ElectricJetpackItem) item;
                boolean hover = jetpack.isHovering(chest);
                if (jetpack.isEngineOn(chest)) {
                    if (InputHandler.isHoldingUp(player) || hover && !player.isOnGround()) {
                        double hoverSpeed = InputHandler.isHoldingDown(player) ? speedHover : speedHoverSlow;

                        double currentAccel = accelVert * (player.getVelocity().getY() < 0.3D ? 2.5D : 1.0D);
                        double currentSpeedVertical = speedVert * (player.isTouchingWater() ? 0.4D : 1.0D);

                        double cost = player.isSprinting() ? flyCost : flyCostSlow;
                        tryUseFuel(stack, cost);

                        if (getStoredFuel(stack) > flyCost && !TechReborn.elytraPredicate.test(player)) {
                            double motionY = player.getVelocity().getY();
                            if (InputHandler.isHoldingUp(player)) {
                                if (!hover) {
                                    this.fly(player, Math.min(motionY + currentAccel, currentSpeedVertical));
                                } else {
                                    if (InputHandler.isHoldingDown(player)) {
                                        this.fly(player, Math.min(motionY + currentAccel, -speedHoverSlow));
                                    } else {
                                        this.fly(player, Math.min(motionY + currentAccel, speedHover));
                                    }
                                }
                            } else {
                                this.fly(player, Math.min(motionY + currentAccel, -hoverSpeed));
                            }

                            float speedSideways = (float) (player.isSneaking() ? speedSide * 0.5F : speedSide);
                            float speedForward = (float) (player.isSprinting() ? speedSideways * sprintSpeed : speedSideways);

                            if (InputHandler.isHoldingForwards(player)) {
                                player.updateVelocity(0.1F, new Vec3d(0, 0, speedForward));
                            }

                            if (InputHandler.isHoldingBackwards(player)) {
                                player.updateVelocity(0.1F, new Vec3d(0, 0, -speedSideways * 0.8F));
                            }

                            if (InputHandler.isHoldingLeft(player)) {
                                player.updateVelocity(0.1F, new Vec3d(speedSideways, 0, 0));
                            }

                            if (InputHandler.isHoldingRight(player)) {
                                player.updateVelocity(0.1F, new Vec3d(-speedSideways, 0, 0));
                            }

                            if (!player.world.isClient()) {
                                player.fallDistance = 0.0F;

                                if (player instanceof ServerPlayerEntity) {
                                    ((ServerPlayNetworkHandlerAccessor) ((ServerPlayerEntity) player).networkHandler).setFloatingTicks(0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRemoved(PlayerEntity playerEntity) {

    }

    @Override
    public double getFuelCapacity() {
        return maxFuel;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) { return true; }

    @Override
    public boolean isDamageable() { return false; }

    @Override
    public double getSpeedSide() { return speedSide; }

    @Override
    public boolean canFly(ItemStack stack) { return getStoredFuel(stack) > 0; }
}
