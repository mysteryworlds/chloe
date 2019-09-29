package com.mysteryworlds.chloe.bukkit.module;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.mysteryworlds.chloe.bukkit.ChloePlugin;
import com.mysteryworlds.chloe.bukkit.vault.VaultEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;

public class ChloeModule extends AbstractModule {

  private final Plugin plugin;
  private final PluginManager pluginManager;
  private final ServicesManager servicesManager;
  private final Configuration configuration;

  private ChloeModule(Plugin plugin, PluginManager pluginManager,
      ServicesManager servicesManager, Configuration configuration) {
    this.plugin = plugin;
    this.pluginManager = pluginManager;
    this.servicesManager = servicesManager;
    this.configuration = configuration;
  }

  public static ChloeModule withPlugin(ChloePlugin chloePlugin) {
    Preconditions.checkNotNull(chloePlugin, "Chloe plugin should not be null");

    Server server = chloePlugin.getServer();
    PluginManager pluginManager = server.getPluginManager();
    ServicesManager servicesManager = server.getServicesManager();

    return new ChloeModule(chloePlugin, pluginManager, servicesManager, chloePlugin.getConfig());
  }

  @Override
  protected void configure() {

    bind(Plugin.class).toInstance(plugin);
    bind(PluginManager.class).toInstance(pluginManager);
    bind(ServicesManager.class).toInstance(servicesManager);

    bindConstant().annotatedWith(Names.named("currencyNameSingular"))
        .to(configuration.getString("currency.singular"));
    bindConstant().annotatedWith(Names.named("currencyNamePlural"))
        .to(configuration.getString("currency.plural"));

    bind(Economy.class).to(VaultEconomy.class);
  }
}
