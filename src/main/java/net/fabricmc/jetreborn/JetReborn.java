package net.fabricmc.jetreborn;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.jetreborn.config.JetRebornConfig;
import net.fabricmc.jetreborn.events.ModRegistry;
import net.fabricmc.jetreborn.network.NetworkHandler;
import net.fabricmc.jetreborn.sound.ModSounds;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reborncore.common.config.Configuration;

import java.util.function.Supplier;

public class JetReborn implements ModInitializer {

    public static final String MOD_ID = "jetreborn";
    public static final String NAME = "Jet Reborn";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static JetReborn INSTANCE;

    public static Supplier<MinecraftServer> serverSupplier = () -> {
        Object instance = FabricLoader.getInstance().getGameInstance();
        if (instance instanceof MinecraftDedicatedServer)
            return (MinecraftServer) instance;
        return null;
    };

    @Override
    public void onInitialize() {
        INSTANCE = this;
        new Configuration(JetRebornConfig.class, "jetreborn");
        ModRegistry.setup();
        ModSounds.register();
        NetworkHandler.onCommonSetup();

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            try {
                Class.forName("net.fabricmc.jetreborn.client.JetRebornClient").getDeclaredMethod("onInitializeClient").invoke(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        LOGGER.info("JetReborn setup done!");
    }
}
