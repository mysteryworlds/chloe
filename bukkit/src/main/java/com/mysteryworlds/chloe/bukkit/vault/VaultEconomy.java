package com.mysteryworlds.chloe.bukkit.vault;

import com.mysteryworlds.chloe.bukkit.bank.EconomyBank;
import com.mysteryworlds.chloe.bukkit.bank.EconomyBankRepository;
import com.mysteryworlds.chloe.bukkit.user.EconomyUser;
import com.mysteryworlds.chloe.bukkit.user.EconomyUserFactory;
import com.mysteryworlds.chloe.bukkit.user.EconomyUserRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public class VaultEconomy extends AbstractEconomy {

    private static final boolean BANK_SUPPORT = true;
    private final Plugin plugin;

    private final EconomyBankRepository bankRepository;
    private final EconomyUserFactory economyUserFactory;
    private final EconomyUserRepository userRepository;

    public VaultEconomy(Plugin plugin,
        EconomyBankRepository bankRepository,
        EconomyUserFactory economyUserFactory,
        EconomyUserRepository userRepository) {
        this.plugin = plugin;
        this.bankRepository = bankRepository;
        this.economyUserFactory = economyUserFactory;
        this.userRepository = userRepository;
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

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        UUID uniqueId = offlinePlayer.getUniqueId();
        return userRepository.findUser(uniqueId).isPresent();
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
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "World specific economy isn't supported");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "World specific economy isn't supported");
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

        return bankRepository.findAll().stream()
            .map(EconomyBank::getName)
            .collect(Collectors.toList());
    }

    @Override
    public boolean createPlayerAccount(String playerName) {

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        UUID uniqueId = offlinePlayer.getUniqueId();
        EconomyUser user = economyUserFactory.createUser(uniqueId);
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {

        return false;
    }
}
