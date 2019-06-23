package com.mysteryworlds.chloe.bukkit;

import com.mysteryworlds.chloe.bukkit.vault.VaultEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class ChloePlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        // Register vault
        if (getServer().getPluginManager().isPluginEnabled("Vault")) {

            getLogger().info("Vault found. Hooking into vault Economy API.");

            VaultEconomy economy = new VaultEconomy(this);
            getServer().getServicesManager().register(Economy.class, economy, this, ServicePriority.Highest);
        }
    }

    @Override
    public void onDisable() {


    }
}
