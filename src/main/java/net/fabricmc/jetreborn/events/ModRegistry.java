package net.fabricmc.jetreborn.events;

import net.fabricmc.jetreborn.init.TRArmorMaterials;
import net.fabricmc.jetreborn.items.armor.JetpackItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import techreborn.utils.InitUtils;

public class ModRegistry {

    public static void setup() {
        registryItems();
    }

    private static void registryItems() {
        final JetpackItem ITEM = InitUtils.setup(new JetpackItem(TRArmorMaterials.JETPACK), "jetpack");

        Registry.register(Registry.ITEM, new Identifier("jetreborn", "jetpack"), ITEM);
    }
}
