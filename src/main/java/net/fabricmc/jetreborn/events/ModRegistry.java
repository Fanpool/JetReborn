package net.fabricmc.jetreborn.events;

import net.fabricmc.jetreborn.config.JetRebornConfig;
import net.fabricmc.jetreborn.init.TRArmorMaterials;
import net.fabricmc.jetreborn.init.TRContent;
import net.fabricmc.jetreborn.items.armor.JetpackItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import reborncore.RebornRegistry;
import reborncore.common.powerSystem.RcEnergyTier;
import techreborn.utils.InitUtils;

public class ModRegistry {

    public static void setup() {
        registryItems();
    }

    private static void registryItems() {
        final JetpackItem ITEM = InitUtils.setup(new JetpackItem(TRArmorMaterials.JETPACK), "jetpack");
        // RebornRegistry.registerItem(TRContent.JETPACK = );
        Registry.register(Registry.ITEM, new Identifier("jetreborn", "jetpack"), ITEM);
    }
}
