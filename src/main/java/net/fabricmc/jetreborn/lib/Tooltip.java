package net.fabricmc.jetreborn.lib;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.Iterator;
import java.util.Objects;

public final class Tooltip {
    private final String translationKey;

    public Tooltip(String translationKey) {
        this.translationKey = Objects.requireNonNull(translationKey);
    }

    public Text color(Formatting formatting) {
        return new TranslatableText(translationKey).formatted(formatting);
    }

    public Text args(Object... args) {
        return new TranslatableText(translationKey, args);
    }

    public String asFormattedString() {
        return new TranslatableText(translationKey).toString();
    }

    public Text build() {
        return new TranslatableText(translationKey);
    }
}