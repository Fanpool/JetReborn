package net.fabricmc.jetreborn.events;

import net.fabricmc.jetreborn.init.ArmorMaterials;
import net.fabricmc.jetreborn.items.armor.ElectricJetpackItem;
import net.fabricmc.jetreborn.items.armor.FuelJetpackItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import techreborn.utils.InitUtils;

public class ModRegistry {

    public static void setup() { registryItems(); }

    private static void registryItems() {
        final ElectricJetpackItem ELECTRIC_JETPACK = InitUtils.setup(new ElectricJetpackItem(ArmorMaterials.JETPACK), "electric_jetpack");
        final FuelJetpackItem FUEL_JETPACK = InitUtils.setup(new FuelJetpackItem(ArmorMaterials.JETPACK), "fuel_jetpack");

        Registry.register(Registry.ITEM, new Identifier("jetreborn", "electric_jetpack"), ELECTRIC_JETPACK);
        Registry.register(Registry.ITEM, new Identifier("jetreborn", "fuel_jetpack"), FUEL_JETPACK);
    }
}
