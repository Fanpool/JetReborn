package net.fabricmc.jetreborn.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.jetreborn.items.CustomModeledArmor;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(ArmorFeatureRenderer.class)
public abstract class MixinArmorFeatureRenderer extends FeatureRenderer {
    @Shadow @Final private static Map<String, Identifier> ARMOR_TEXTURE_CACHE;
    
    public MixinArmorFeatureRenderer(FeatureRendererContext context) {
        super(context);
    }
    
    @Unique
    private LivingEntity storedEntity;
    
    @Inject(method = "renderArmor", at = @At("HEAD"))
    private void storeEntity(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity livingEntity, EquipmentSlot armorSlot, int light, BipedEntityModel model, CallbackInfo ci) {
        this.storedEntity = livingEntity;
    }
    
    @Inject(method = "getArmor", at = @At("RETURN"), cancellable = true)
    private void getArmor(EquipmentSlot equipmentSlot, CallbackInfoReturnable<BipedEntityModel> cir) {
        if (equipmentSlot == EquipmentSlot.CHEST) {
            if (!(storedEntity == null)) {
                ItemStack stack = storedEntity.getEquippedStack(equipmentSlot);
                if (stack.getItem() instanceof CustomModeledArmor) {
                    BipedEntityModel model = ((CustomModeledArmor) stack.getItem()).getArmorModel(storedEntity, stack, equipmentSlot, cir.getReturnValue());
                    if (model != cir.getReturnValue())
                        cir.setReturnValue(model);
                }
            }
        }
    }
    
    @Inject(method = "getArmorTexture", at = @At("HEAD"), cancellable = true)
    private void getArmorTexture(ArmorItem armorItem, boolean lowerParts, String suffix, CallbackInfoReturnable<Identifier> cir) {
        if (armorItem instanceof CustomModeledArmor) {
            String model = ((CustomModeledArmor) armorItem).getArmorTexture(suffix);
            cir.setReturnValue(ARMOR_TEXTURE_CACHE.computeIfAbsent(model, Identifier::new));
        }
    }
}
