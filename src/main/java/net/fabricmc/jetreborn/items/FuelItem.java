package net.fabricmc.jetreborn.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

public interface FuelItem {
    String FUEL_KEY = "fuel";

    double getFuelCapacity();


    default double getStoredFuel(ItemStack stack) {
        if (stack.getCount() != 1) {
            throw new IllegalArgumentException("Invalid count: " + stack.getCount());
        }
        return getStoredFuelUnchecked(stack);
    }

    default void setStoredFuel(ItemStack stack, double newAmount) {
        if (stack.getCount() != 1) {
            throw new IllegalArgumentException("Invalid count: " + stack.getCount());
        }
        setStoredFuelUnchecked(stack, newAmount);
    }

    default boolean tryUseFuel(ItemStack stack, double amount) {
        double newAmount = getStoredFuel(stack) - amount;
        if (newAmount < 0) {
            return false;
        } else {
            setStoredFuel(stack, newAmount);
            return true;
        }
    }

    static double getStoredFuelUnchecked(ItemStack stack) {
        @Nullable NbtCompound nbt = stack.getNbt();
        return nbt != null ? nbt.getDouble(FUEL_KEY) : 0;
    }

    static void setStoredFuelUnchecked(ItemStack stack, double newAmount) {
        if (newAmount == 0) {
            stack.removeSubNbt(FUEL_KEY);
        } else {
            stack.getOrCreateNbt().putDouble(FUEL_KEY, newAmount);
        }
    }

}
