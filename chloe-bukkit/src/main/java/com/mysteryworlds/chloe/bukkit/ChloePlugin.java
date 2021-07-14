package com.mysteryworlds.chloe.bukkit;

import com.mysteryworlds.chloe.bukkit.module.ChloeModule;
import de.d3adspace.theresa.TheresaFactory;
import javax.inject.Inject;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ChloePlugin extends JavaPlugin {

  @Inject
  private ServicesManager servicesManager;
  @Inject
  private PluginManager pluginManager;
  @Inject
  private Economy economy;

  @Override
  public void onEnable() {
    var injectionModule = ChloeModule.withPlugin(this);
    var injector = TheresaFactory.create(injectionModule);
    injector.injectMembers(this);

    setupMetrics();

    // Register vault
    boolean vaultEnabled = pluginManager.isPluginEnabled("Vault");
    if (vaultEnabled) {
      getLogger().info("Vault found. Hooking into vault Economy API.");

      servicesManager.register(Economy.class, economy, this,
        ServicePriority.Highest);
    }
  }

  private void setupMetrics() {
    getLogger().info("Setting up Metrics.");
    Metrics metrics = new Metrics(this, 12047);
  }

  @Override
  public void onDisable() {

  }
}
