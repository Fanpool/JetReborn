package net.fabricmc.jetreborn.util;

import net.fabricmc.jetreborn.handler.InputHandler;
import net.fabricmc.jetreborn.items.armor.JetpackItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class JetpackUtils {
    public static boolean isFlying(PlayerEntity player) {
        ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
        if (!stack.isEmpty()) {
            Item item = stack.getItem();
            if (item instanceof JetpackItem) {
                JetpackItem jetpack = (JetpackItem) item;
                if (jetpack.isEngineOn(stack) && (jetpack.getStoredEnergy(stack) > 0 || player.isCreative())) {
                    if (jetpack.isHovering(stack)) {
                        return !player.isOnGround();
                    } else {
                        return InputHandler.isHoldingUp(player);
                    }
                }
            }
        }

        return false;
    }
}
