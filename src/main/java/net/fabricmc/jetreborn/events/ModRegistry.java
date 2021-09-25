package net.fabricmc.jetreborn.events;

import net.fabricmc.jetreborn.init.TRArmorMaterials;
import net.fabricmc.jetreborn.items.armor.ElectricJetpackItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import techreborn.utils.InitUtils;

public class ModRegistry {

    public static void setup() {
        registryItems();
    }

    private static void registryItems() {
        final ElectricJetpackItem ITEM = InitUtils.setup(new ElectricJetpackItem(TRArmorMaterials.JETPACK), "jetpack");

        Registry.register(Registry.ITEM, new Identifier("jetreborn", "jetpack"), ITEM);
    }
}
