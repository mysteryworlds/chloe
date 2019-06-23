package com.mysteryworlds.chloe.bukkit;

import com.mysteryworlds.chloe.bukkit.service.EconomyService;
import com.mysteryworlds.chloe.bukkit.service.EconomyServiceImpl;
import com.mysteryworlds.chloe.bukkit.vault.VaultEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class ChloePlugin extends JavaPlugin {

    private EconomyService economyService;

    @Override
    public void onEnable() {

        economyService = new EconomyServiceImpl(null, null);

        // Register vault
        if (getServer().getPluginManager().isPluginEnabled("Vault")) {

            getLogger().info("Vault found. Hooking into vault Economy API.");

            VaultEconomy economy = new VaultEconomy(this, economyService);
            getServer().getServicesManager().register(Economy.class, economy, this, ServicePriority.Highest);
        }
    }

    @Override
    public void onDisable() {


    }
}
