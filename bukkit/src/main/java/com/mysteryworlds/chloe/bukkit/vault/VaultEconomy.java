package com.mysteryworlds.chloe.bukkit.vault;

import com.google.common.base.Preconditions;
import com.mysteryworlds.chloe.bukkit.bank.EconomyBank;
import com.mysteryworlds.chloe.bukkit.bank.EconomyBankRepository;
import com.mysteryworlds.chloe.bukkit.user.EconomyUser;
import com.mysteryworlds.chloe.bukkit.user.EconomyUserFactory;
import com.mysteryworlds.chloe.bukkit.user.EconomyUserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.plugin.Plugin;

@Singleton
public class VaultEconomy extends AbstractEconomy {

  private static final String ERROR_ACCOUNT_NOT_FOUND = "Couldn't find account";
  private static final String ERROR_NEGATIVE_FUNDS = "Cannot withdraw negative funds";
  private static final String ERROR_INSUFFICIENT_FUNDS = "Insufficient funds";

  private static final boolean BANK_SUPPORT = true;
  private final Plugin plugin;

  private final String currencyNameSingular;
  private final String currencyNamePlural;

  private final EconomyBankRepository bankRepository;
  private final EconomyUserFactory economyUserFactory;
  private final EconomyUserRepository userRepository;

  /**
   * Create a new vault economy instance.
   *
   * @param plugin The chloe plugin.
   * @param currencyNameSingular The singular name of the currency.
   * @param currencyNamePlural The plural name of the currency.
   * @param bankRepository The bank repository.
   * @param economyUserFactory The user factory.
   * @param userRepository The user repository.
   */
  @Inject
  public VaultEconomy(Plugin plugin,
      @Named("currencyNameSingular") String currencyNameSingular,
      @Named("currencyNamePlural") String currencyNamePlural, EconomyBankRepository bankRepository,
      EconomyUserFactory economyUserFactory,
      EconomyUserRepository userRepository) {
    this.plugin = plugin;
    this.currencyNameSingular = currencyNameSingular;
    this.currencyNamePlural = currencyNamePlural;
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
    if (amount == 1.0) {
      return String.format("%f %s", amount, currencyNameSingular());
    }

    return String.format("%f %s", amount, currencyNamePlural());
  }

  @Override
  public String currencyNamePlural() {
    return currencyNamePlural;
  }

  @Override
  public String currencyNameSingular() {
    return currencyNameSingular;
  }

  @Override
  public boolean hasAccount(String playerName) {
    Preconditions.checkNotNull(playerName, "Player name should not be null");

    return userRepository.findUserByName(playerName).isPresent();
  }

  @Override
  public boolean hasAccount(String playerName, String worldName) {
    return false;
  }

  @Override
  public double getBalance(String playerName) {
    Preconditions.checkNotNull(playerName, "Player name should not be null");

    Optional<EconomyUser> userOptional = userRepository.findUserByName(playerName);
    return userOptional.map(EconomyUser::getBalance).orElse(-1D);
  }

  @Override
  public double getBalance(String playerName, String world) {
    return -1D;
  }

  @Override
  public boolean has(String playerName, double amount) {
    Preconditions.checkNotNull(playerName, "Player name should not be null");

    Optional<EconomyUser> userOptional = userRepository.findUserByName(playerName);
    return userOptional.map(user -> user.hasBalance(amount)).orElse(false);
  }

  @Override
  public boolean has(String playerName, String worldName, double amount) {
    return false;
  }

  @Override
  public EconomyResponse withdrawPlayer(String playerName, double amount) {
    Preconditions.checkNotNull(playerName, "Player name should not be null");

    if (amount < 0) {
      return new EconomyResponse(0, 0, ResponseType.FAILURE, ERROR_NEGATIVE_FUNDS);
    }

    Optional<EconomyUser> economyUserOptional = userRepository.findUserByName(playerName);
    if (economyUserOptional.isEmpty()) {
      return new EconomyResponse(0, 0, ResponseType.FAILURE, ERROR_ACCOUNT_NOT_FOUND);
    }

    EconomyUser economyUser = economyUserOptional.get();

    if (!economyUser.hasBalance(amount)) {
      double balance = economyUser.getBalance();
      return new EconomyResponse(0, balance, ResponseType.FAILURE, ERROR_INSUFFICIENT_FUNDS);
    }

    double newBalance = economyUser.subtractBalance(amount);
    return new EconomyResponse(amount, newBalance, ResponseType.SUCCESS, "");
  }

  @Override
  public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
    return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED,
        "World specific economy isn't supported");
  }

  @Override
  public EconomyResponse depositPlayer(String playerName, double amount) {
    Preconditions.checkNotNull(playerName, "Player name should not be null");

    if (amount < 0) {
      return new EconomyResponse(0, 0, ResponseType.FAILURE, ERROR_NEGATIVE_FUNDS);
    }

    Optional<EconomyUser> economyUserOptional = userRepository.findUserByName(playerName);
    if (economyUserOptional.isEmpty()) {
      return new EconomyResponse(0, 0, ResponseType.FAILURE, ERROR_ACCOUNT_NOT_FOUND);
    }

    EconomyUser economyUser = economyUserOptional.get();
    double newBalance = economyUser.addBalance(amount);

    return new EconomyResponse(amount, newBalance, ResponseType.SUCCESS, "");
  }

  @Override
  public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
    return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED,
        "World specific economy isn't supported");
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

    EconomyUser user = economyUserFactory.createUser(playerName);
    userRepository.save(user);

    return true;
  }

  @Override
  public boolean createPlayerAccount(String playerName, String worldName) {

    return false;
  }
}
