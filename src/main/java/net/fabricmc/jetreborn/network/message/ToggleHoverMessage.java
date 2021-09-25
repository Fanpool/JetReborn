package net.fabricmc.jetreborn.network.message;

import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.jetreborn.items.armor.ElectricJetpackItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;


public class ToggleHoverMessage {
    public static ToggleHoverMessage read(PacketByteBuf buffer) {
        return new ToggleHoverMessage();
    }
    
    public static void write(ToggleHoverMessage message, PacketByteBuf buffer) {
        
    }
    
    public static void onMessage(ToggleHoverMessage message, PacketContext context) {
        context.getTaskQueue().execute(() -> {
            ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
            if (player != null) {
                ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
                Item item = stack.getItem();
                if (item instanceof ElectricJetpackItem) {
                    ElectricJetpackItem jetpack = (ElectricJetpackItem) item;
                    jetpack.toggleHover(stack);
                }
            }
        });
    }
}
