package com.mysteryworlds.chloe.bukkit;

import com.mysteryworlds.chloe.bukkit.vault.VaultEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ChloePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();
        ServicesManager servicesManager = getServer().getServicesManager();

        setupMetrics();

        // Register vault
        boolean vaultEnabled = pluginManager.isPluginEnabled("Vault");
        if (vaultEnabled) {
            getLogger().info("Vault found. Hooking into vault Economy API.");

            VaultEconomy economy = new VaultEconomy(this, null, null, null);
            servicesManager.register(Economy.class, economy, this, ServicePriority.Highest);
        }
    }

    private void setupMetrics() {
        getLogger().info("Setting up Metrics.");
        Metrics metrics = new Metrics(this);
    }

    @Override
    public void onDisable() {


    }
}
