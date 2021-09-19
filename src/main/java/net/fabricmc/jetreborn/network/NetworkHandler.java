package net.fabricmc.jetreborn.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.jetreborn.JetReborn;
import net.fabricmc.jetreborn.network.message.ToggleEngineMessage;
import net.fabricmc.jetreborn.network.message.ToggleHoverMessage;
import net.fabricmc.jetreborn.network.message.UpdateInputMessage;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkHandler {
    public static final Identifier PACKET_ID = new Identifier(JetReborn.MOD_ID, JetReborn.MOD_ID);
    private static int id = 0;
    
    public static void onCommonSetup() {
        ServerSidePacketRegistry.INSTANCE.register(PACKET_ID, (packetContext, packetByteBuf) -> {
            int id = packetByteBuf.readInt();
            switch (id) {
                case 0: {
                    ToggleHoverMessage.onMessage(ToggleHoverMessage.read(packetByteBuf), packetContext);
                    break;
                }
                case 1: {
                    UpdateInputMessage.onMessage(UpdateInputMessage.read(packetByteBuf), packetContext);
                    break;
                }
                case 2: {
                    ToggleEngineMessage.onMessage(ToggleEngineMessage.read(packetByteBuf), packetContext);
                    break;
                }
            }
        });
    }
    
    @Environment(EnvType.CLIENT)
    public static void sendToServer(ToggleHoverMessage message) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(0);
        ToggleHoverMessage.write(message, buf);
        ClientSidePacketRegistry.INSTANCE.sendToServer(PACKET_ID, buf);
    }
    
    @Environment(EnvType.CLIENT)
    public static void sendToServer(UpdateInputMessage message) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(1);
        UpdateInputMessage.write(message, buf);
        ClientSidePacketRegistry.INSTANCE.sendToServer(PACKET_ID, buf);
    }
    
    @Environment(EnvType.CLIENT)
    public static void sendToServer(ToggleEngineMessage message) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(2);
        ToggleEngineMessage.write(message, buf);
        ClientSidePacketRegistry.INSTANCE.sendToServer(PACKET_ID, buf);
    }
}
