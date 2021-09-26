package net.fabricmc.jetreborn.events;

import net.fabricmc.jetreborn.init.TRArmorMaterials;
import net.fabricmc.jetreborn.items.armor.ElectricJetpackItem;
import net.fabricmc.jetreborn.items.armor.FuelJetpackItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import techreborn.utils.InitUtils;

public class ModRegistry {

    public static void setup() {
        registryItems();
    }

    private static void registryItems() {
        final ElectricJetpackItem ELECTRIC_JETPACK = InitUtils.setup(new ElectricJetpackItem(TRArmorMaterials.JETPACK), "Electric jetpack");
        final FuelJetpackItem FUEL_JETPACK = InitUtils.setup(new FuelJetpackItem(TRArmorMaterials.JETPACK), "Fuel jetpack");

        Registry.register(Registry.ITEM, new Identifier("jetreborn", "electricJetpack"), ELECTRIC_JETPACK);
        Registry.register(Registry.ITEM, new Identifier("jetreborn", "fuelJetpack"), FUEL_JETPACK);
    }
}
