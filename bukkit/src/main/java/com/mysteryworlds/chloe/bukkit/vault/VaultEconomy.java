package com.mysteryworlds.chloe.bukkit.vault;

import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class VaultEconomy extends AbstractEconomy {

    private static final boolean BANK_SUPPORT = true;
    private final Plugin plugin;

    public VaultEconomy(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isEnabled() {

        return plugin.isEnabled();
    }

    @Override
    public String getName() {

        return plugin.getName();
    }

    @Override
    public boolean hasBankSupport() {

        return BANK_SUPPORT;
    }

    @Override
    public int fractionalDigits() {

        return 2;
    }

    @Override
    public String format(double amount) {
        return null;
    }

    @Override
    public String currencyNamePlural() {

        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String playerName) {
        return false;
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return false;
    }

    @Override
    public double getBalance(String playerName) {
        return 0;
    }

    @Override
    public double getBalance(String playerName, String world) {
        return 0;
    }

    @Override
    public boolean has(String playerName, double amount) {
        return false;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return false;
    }
}
