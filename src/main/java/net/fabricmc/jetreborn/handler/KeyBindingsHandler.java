package net.fabricmc.jetreborn.handler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.jetreborn.JetReborn;
import net.fabricmc.jetreborn.items.armor.ElectricJetpackItem;
import net.fabricmc.jetreborn.lib.ModTooltips;
import net.fabricmc.jetreborn.network.NetworkHandler;
import net.fabricmc.jetreborn.network.message.ToggleEngineMessage;
import net.fabricmc.jetreborn.network.message.ToggleHoverMessage;
import net.fabricmc.jetreborn.network.message.UpdateInputMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyBindingsHandler {
    private static KeyBinding keyHover;
    private static KeyBinding keyEngine;
    private static KeyBinding keyDescend;

    private static boolean up = false;
    private static boolean down = false;
    private static boolean forwards = false;
    private static boolean backwards = false;
    private static boolean left = false;
    private static boolean right = false;

    public static void onClientSetup() {
        KeyBindingHelper.registerKeyBinding(keyHover = new KeyBinding(
                "key.jetreborn.hover",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                JetReborn.NAME
        ));
        KeyBindingHelper.registerKeyBinding(keyEngine = new KeyBinding(
                "key.jetreborn.engine",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                JetReborn.NAME
        ));
        KeyBindingHelper.registerKeyBinding(keyDescend = new KeyBinding(
                "key.jetreborn.descend",
                InputUtil.Type.KEYSYM,
                InputUtil.UNKNOWN_KEY.getCode(),
                JetReborn.NAME
        ));
    }

    public static void onClientTick(MinecraftClient client) {
        handleInputs(client);
        updateInputs(client);
    }

    private static void handleInputs(MinecraftClient client) {
        PlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        Item item = chest.getItem();

        if (item instanceof ElectricJetpackItem) {
            ElectricJetpackItem jetpack = (ElectricJetpackItem) item;

            while (keyEngine.wasPressed()) {
                NetworkHandler.sendToServer(new ToggleEngineMessage());
                boolean on = !jetpack.isEngineOn(chest);
                Text state = on ? ModTooltips.ON.color(Formatting.GREEN) : ModTooltips.OFF.color(Formatting.RED);
                player.sendMessage(ModTooltips.TOGGLE_ENGINE.args(state), true);
            }

            while (keyHover.wasPressed()) {
                NetworkHandler.sendToServer(new ToggleHoverMessage());
                boolean on = !jetpack.isHovering(chest);
                Text state = on ? ModTooltips.ON.color(Formatting.GREEN) : ModTooltips.OFF.color(Formatting.RED);
                player.sendMessage(ModTooltips.TOGGLE_HOVER.args(state), true);
            }
        }
    }

    public static void updateInputs(MinecraftClient client) {
        GameOptions settings = client.options;

        if (client.getNetworkHandler() == null) {
            return;
        }

        boolean upNow = settings.keyJump.isPressed();
        boolean downNow = keyDescend.isUnbound() ? settings.keySneak.isPressed() : keyDescend.isPressed();
        boolean forwardsNow = settings.keyForward.isPressed();
        boolean backwardsNow = settings.keyBack.isPressed();
        boolean leftNow = settings.keyLeft.isPressed();
        boolean rightNow = settings.keyRight.isPressed();

        if (upNow != up || downNow != down || forwardsNow != forwards || backwardsNow != backwards || leftNow != left || rightNow != right) {
            up = upNow;
            down = downNow;
            forwards = forwardsNow;
            backwards = backwardsNow;
            left = leftNow;
            right = rightNow;

            NetworkHandler.sendToServer(new UpdateInputMessage(upNow, downNow, forwardsNow, backwardsNow, leftNow, rightNow));
            InputHandler.update(client.player, upNow, downNow, forwardsNow, backwardsNow, leftNow, rightNow);
        }
    }
}
