package net.fabricmc.jetreborn.client;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.jetreborn.items.FuelItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import reborncore.common.powerSystem.PowerSystem;
import reborncore.common.util.StringUtils;

import java.util.List;


public class FuelStackToolTipHandler implements ItemTooltipCallback {

    @Override
    public void getTooltip(ItemStack itemstack, TooltipContext tooltipContext, List<Text> tooltipLines) {
        Item item = itemstack.getItem();
        if (item instanceof FuelItem fuelItem) {
            LiteralText line1 = new LiteralText(PowerSystem.getLocalizedPowerFullNoSuffix(fuelItem.getStoredFuel(itemstack)));
            line1.append("/");
            line1.append(PowerSystem.getLocalizedPowerNoSuffix(fuelItem.getFuelCapacity()));
            line1.append(" mB");
            line1.formatted(Formatting.GOLD);

            tooltipLines.add(1, line1);

            if (Screen.hasShiftDown()) {
                int percentage = percentage(fuelItem.getStoredFuel(itemstack), fuelItem.getFuelCapacity());
                MutableText line2 = StringUtils.getPercentageText(percentage);
                line2.append(" ");
                line2.formatted(Formatting.GRAY);
                line2.append(I18n.translate("jetreborn.gui.tooltip.power_charged"));
                tooltipLines.add(2, line2);
            }
        }
    }

    private int percentage(double CurrentValue, double MaxValue) {
        if (CurrentValue == 0) {
            return 0;
        }
        return (int) ((CurrentValue * 100.0f) / MaxValue);
    }
}
