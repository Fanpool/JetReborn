package net.fabricmc.jetreborn.network.message;


import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.jetreborn.items.Jetpack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;


public class ToggleEngineMessage {
    public static ToggleEngineMessage read(PacketByteBuf buffer) {
        return new ToggleEngineMessage();
    }
    
    public static void write(ToggleEngineMessage message, PacketByteBuf buffer) {
        
    }
    
    public static void onMessage(ToggleEngineMessage message, PacketContext context) {
        context.getTaskQueue().execute(() -> {
            ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
            if (player != null) {
                ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
                Item item = stack.getItem();
                if (item instanceof Jetpack jetpack) {
                    jetpack.toggleEngine(stack);
                }
            }
        });
    }
}
