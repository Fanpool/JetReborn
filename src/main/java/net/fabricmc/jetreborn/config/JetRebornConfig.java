package net.fabricmc.jetreborn.config;

import reborncore.common.config.Config;

public class JetRebornConfig {

    @Config(config = "items", category = "power", key = "enableJetpackParticles", comment = "Enable Jetpack particles")
    public static boolean enableJetpackParticles = true;

    @Config(config = "items", category = "power", key = "jetpackEnableFlight", comment = "Enable Flight on jetpack")
    public static boolean jetpackEnableFlight = true;

    @Config(config = "items", category = "items", key = "enableJetpackSounds", comment = "Enable Jetpack sounds")
    public static boolean enableJetpackSounds = true;

    @Config(config = "items", category = "power", key = "electricJetpackMaxEnergy", comment = "Energy Capacity for Basic Jetpack")
    public static int electricJetpackCharge = 100_000;

    @Config(config = "items", category = "power", key = "electricJeypackFlyingCost", comment = "Jetpack Flying Cost")
    public static long electricJetpackFlyingCost = 30;

    @Config(config = "items", category = "power", key = "electricJetpackFlyingCostSlow", comment = "Jetpack Flying Cost")
    public static long electricJetpackFlyingCostSlow = 18;

    @Config(config = "items", category = "power", key = "electricJetpackSpeedHover", comment = "Jetpack Hover Speed")
    public static double electricJetpackSpeedHover = 0.27D;

    @Config(config = "items", category = "power", key = "electricJetpackSpeedHoverSlow", comment = "Jetpack Speed Hover Slow")
    public static double electricJetpackSpeedHoverSlow = 0.075D;

    @Config(config = "items", category = "power", key = "electricJetpackSpeedVert", comment = "Jetpack Speed Vert")
    public static double electricJetpackSpeedVert = 0.23D;

    @Config(config = "items", category = "power", key = "electricJetpackAccelVert", comment = "Jetpack Accel Vert")
    public static double electricJetpackAccelVert = 0.12D;

    @Config(config = "items", category = "power", key = "electricJetpackSpeedSide", comment = "Jetpack Speed Side")
    public static double electricJetpackSpeedSide = 0.2D;

    @Config(config = "items", category = "power", key = "electricJetpackSprintSpeed", comment = "Jetpack Sprint Speed")
    public static double electricJetpackSprintSpeed = 1.1D;

    @Config(config = "items", category = "power", key = "electricJetpackArmor", comment  = "Fuel jetpack armor")
    public static short electricJetpackArmor = 3;

    @Config(config = "items", category = "power", key = "fuelJetpackMaxFuel", comment = "FuelJetpackMaxFuel")
    public static double fuelJetpackMaxFuel = 100D;

    @Config(config = "items", category = "power", key = "fuelJetpackFlyCost", comment = "FuelJetpackFlyCost")
    public static double fuelJetpackFlyCost = 0.3D;

    @Config(config = "items", category = "power", key = "fuelJetpackFlyCostLow", comment = "FuelJetpackFlyCostLow")
    public static double fuelJetpackFlyCostLow = 0.18D;

    @Config(config = "items", category = "power", key = "fuelJetpackSpeedVert", comment = "FuelJetpackSpeedVert")
    public static double fuelJetpackSpeedVert = 0.2D;

    @Config(config = "items", category = "power", key = "fuelJetpackSpeedSide", comment = "fuelJetpackSpeedSide")
    public static double fuelJetpackSpeedSide = 0.2D;

    @Config(config = "items", category = "power", key = "fuelJetpackAccelVert", comment = "fuelJetpackAccelVert")
    public static double fuelJetpackAccelVert = 0.12D;

    @Config(config = "items", category = "power", key = "fuelJetpackSprintSpeed", comment = "fuelJetpackSpringSpeed")
    public static double fuelJetpackSprintSpeed = 1.1D;

    @Config(config = "items", category = "power", key = "fuelJetpackSpeedHover", comment = "fuelJetpackSpeedHover")
    public static double fuelJetpackSpeedHover = 0.27D;

    @Config(config = "items", category = "power", key = "fuelJetpackSpeedHoverSlow", comment = "fuelJetpackSpeedSlow")
    public static double fuelJetpackSpeedHoverSlow = 0.075D;
}
