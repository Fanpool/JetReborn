package net.fabricmc.jetreborn.client;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.jetreborn.JetReborn;
import net.fabricmc.jetreborn.config.ModConfigs;
import net.fabricmc.jetreborn.handler.JetpackClientHandler;
import net.fabricmc.jetreborn.handler.KeyBindingsHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class JetRebornClient {
    public static void onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register(KeyBindingsHandler::onClientTick);
        ClientTickEvents.END_CLIENT_TICK.register(JetpackClientHandler::onClientTick);

        KeyBindingsHandler.onClientSetup();

        AutoConfig.register(ModConfigs.Client.class, JanksonConfigSerializer::new);

        Supplier<MinecraftServer> oldServerSupplier = JetReborn.serverSupplier;
        JetReborn.serverSupplier = () -> {
            IntegratedServer server = MinecraftClient.getInstance().getServer();
            if (server != null) return server;
            return oldServerSupplier.get();
        };
    }
}
