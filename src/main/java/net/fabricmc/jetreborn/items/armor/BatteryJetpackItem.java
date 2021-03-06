package net.fabricmc.jetreborn.items.armor;

import com.google.common.collect.Multimap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.jetreborn.config.JetRebornConfig;
import net.fabricmc.jetreborn.handler.InputHandler;
import net.fabricmc.jetreborn.items.Jetpack;
import net.fabricmc.jetreborn.mixin.ServerPlayNetworkHandlerAccessor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import reborncore.api.items.ArmorBlockEntityTicker;
import reborncore.api.items.ItemStackModifiers;
import reborncore.common.powerSystem.PowerSystem;
import reborncore.common.powerSystem.RcEnergyItem;
import reborncore.common.powerSystem.RcEnergyTier;
import reborncore.common.util.ItemUtils;
import techreborn.TechReborn;
import techreborn.items.armor.TRArmourItem;
import techreborn.utils.InitUtils;

public class BatteryJetpackItem extends TRArmourItem implements ItemStackModifiers, ArmorBlockEntityTicker, RcEnergyItem, Jetpack {

    public final long maxCharge = JetRebornConfig.batteryJetpackCharge;
    public final long flyCost = JetRebornConfig.batteryJetpackFlyingCost;
    public final long flyCostSlow = JetRebornConfig.batteryJetpackFlyingCostSlow;
    public final boolean enableFlight = JetRebornConfig.jetpackEnableFlight;
    public final short armor = JetRebornConfig.batteryJetpackArmor;
    public final double accelVert = JetRebornConfig.batteryJetpackAccelVert;
    public final double speedVert = JetRebornConfig.batteryJetpackSpeedVert;
    public final double speedHover = JetRebornConfig.batteryJetpackSpeedHover;
    public final double speedHoverSlow = JetRebornConfig.batteryJetpackSpeedHoverSlow;
    public final double speedSide = JetRebornConfig.batteryJetpackSpeedSide;
    public final double sprintSpeed = JetRebornConfig.batteryJetpackSprintSpeed;

    public BatteryJetpackItem(ArmorMaterial material) {
        super(material, EquipmentSlot.CHEST, new Settings().group(TechReborn.ITEMGROUP).maxCount(1).maxDamage(-1));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> itemList) {
        if (!isIn(group)) {
            return;
        }
        InitUtils.initPoweredItems(this, itemList);
    }

    @Override
    public double getSpeedSide() {
        return 0;
    }

    @Override
    public boolean canFly(ItemStack stack) { return getStoredEnergy(stack) > flyCost; }

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

                        long cost = player.isSprinting() ? flyCost : flyCostSlow;
                        tryUseEnergy(stack, cost);

                        if (getStoredEnergy(stack) > flyCost && !TechReborn.elytraPredicate.test(player)) {
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
    public void getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack, Multimap<EntityAttribute, EntityAttributeModifier> attributes) {
        if (equipmentSlot == this.slot) {
            attributes.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(MODIFIERS[slot.getEntitySlotId()], "Armor modifier", armor, EntityAttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public int getDurabilityColor(ItemStack stack) {
        return PowerSystem.getDisplayPower().colour;
    }

    @Override
    public boolean showDurability(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurability(ItemStack stack) { return 1 - ItemUtils.getPowerForDurabilityBar(stack); }

    @Override
    public boolean isDamageable() { return false; }

    @Override
    public long getEnergyCapacity() { return maxCharge; }

    @Override
    public RcEnergyTier getTier() { return RcEnergyTier.HIGH; }
}
