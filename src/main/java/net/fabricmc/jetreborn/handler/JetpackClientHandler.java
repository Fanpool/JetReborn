package net.fabricmc.jetreborn.handler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.jetreborn.config.JetRebornConfig;
import net.fabricmc.jetreborn.config.ModConfigs;
import net.fabricmc.jetreborn.items.armor.JetpackItem;
import net.fabricmc.jetreborn.sound.JetpackSound;
import net.fabricmc.jetreborn.util.JetpackUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class JetpackClientHandler {
    private static final Random RANDOM = new Random();

    public static void onClientTick(MinecraftClient client) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null && mc.world != null) {
            if (!mc.isPaused()) {
                ItemStack chest = mc.player.getEquippedStack(EquipmentSlot.CHEST);
                Item item = chest.getItem();
                if (!chest.isEmpty() && item instanceof JetpackItem && JetpackUtils.isFlying(mc.player)) {
                    if (ModConfigs.getClient().general.enableJetpackParticles && (mc.options.particles != ParticlesMode.MINIMAL)) {
                        Vec3d playerPos = mc.player.getPos().add(0, 1.5, 0);

                        float random = (RANDOM.nextFloat() - 0.5F) * 0.1F;
                        double[] sneakBonus = mc.player.isSneaking() ? new double[]{-0.30, -0.10} : new double[]{0, 0};

                        Vec3d vLeft = new Vec3d(-0.18, -0.90 + sneakBonus[1], -0.30 + sneakBonus[0]).rotateX(0).rotateY(mc.player.bodyYaw * -0.017453292F);
                        Vec3d vRight = new Vec3d(0.18, -0.90 + sneakBonus[1], -0.30 + sneakBonus[0]).rotateX(0).rotateY(mc.player.bodyYaw * -0.017453292F);

                        Vec3d v = playerPos.add(vLeft).add(mc.player.getVelocity().multiply(JetRebornConfig.jetpackSpeedSide));
                        mc.particleManager.addParticle(ParticleTypes.FLAME, v.x, v.y, v.z, random, -0.2D, random);
                        mc.particleManager.addParticle(ParticleTypes.SMOKE, v.x, v.y, v.z, random, -0.2D, random);

                        v = playerPos.add(vRight).add(mc.player.getVelocity().multiply(JetRebornConfig.jetpackSpeedSide));
                        mc.particleManager.addParticle(ParticleTypes.FLAME, v.x, v.y, v.z, random, -0.2D, random);
                        mc.particleManager.addParticle(ParticleTypes.SMOKE, v.x, v.y, v.z, random, -0.2D, random);
                    }

                    if (ModConfigs.getClient().general.enableJetpackSounds && !JetpackSound.playing(mc.player.getId())) {
                        mc.getSoundManager().play(new JetpackSound(mc.player));
                    }
                }
            }
        }
    }
}
