package net.fabricmc.jetreborn.config;

import reborncore.common.config.Config;

public class JetRebornConfig {

    @Config(config = "items", category = "power", key = "BasicJetpackMaxEnergy", comment = "Energy Capacity for Basic Jetpack")
    public static int basicJetpackCharge = 100_000;

    @Config(config = "items", category = "power", key = "jetpackEnableFlight", comment = "Enable Flight or jetpack")
    public static boolean jetpackEnableFlight = true;

    @Config(config = "items", category = "power", key = "jeypackFlyingCost", comment = "Jetpack Flying Cost")
    public static long jetpackFlyingCost = 30;

    @Config(config = "items", category = "power", key = "jetpackFlyingCostSlow", comment = "Jetpack Flying Cost")
    public static long jetpackFlyingCostSlow = 18;

    @Config(config = "items", category = "power", key = "jetpackSpeedHover", comment = "Jetpack Hover Speed")
    public static double jetpackSpeedHover = 0.27D;

    @Config(config = "items", category = "power", key = "jetpackSpeedHoverSlow", comment = "Jetpack Speed Hover Slow")
    public static double jetpackSpeedHoverSlow = 0.075D;

    @Config(config = "items", category = "power", key = "jetpackSpeedVert", comment = "Jetpack Speed Vert")
    public static double jetpackSpeedVert = 0.23D;

    @Config(config = "items", category = "power", key = "jetpackAccelVert", comment = "Jetpack Accel Vert")
    public static double jetpackAccelVert = 0.12D;

    @Config(config = "items", category = "power", key = "jetpackSpeedSide", comment = "Jetpack Speed Side")
    public static double jetpackSpeedSide = 0.2D;

    @Config(config = "items", category = "power", key = "jetpackSprintSpeed", comment = "Jetpack Sprint Speed")
    public static double jetpackSprintSpeed = 1.1D;

}
