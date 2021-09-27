package net.fabricmc.jetreborn.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

public interface Jetpack {

    double flyCost = 1.0d;

    double getSpeedSide();
    boolean canFly(ItemStack stack);

    default boolean isHovering(ItemStack stack) {
        NbtCompound tag = stack.getNbt();
        return tag != null && tag.contains("Hover") && tag.getBoolean("Hover");
    }

    default boolean toggleHover(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        boolean current = tag.contains("Hover") && tag.getBoolean("Hover");
        tag.putBoolean("Hover", !current);
        return !current;
    }

    default boolean toggleEngine(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        boolean current = tag.contains("Engine") && tag.getBoolean("Engine");
        tag.putBoolean("Engine", !current);
        return !current;
    }

    default void fly(PlayerEntity player, double y) {
        Vec3d motion = player.getVelocity();
        player.setVelocity(motion.getX(), y, motion.getZ());
    }

    default boolean isEngineOn(ItemStack stack) {
        NbtCompound tag = stack.getNbt();
        return tag != null && tag.contains("Engine") && tag.getBoolean("Engine");
    }
}
