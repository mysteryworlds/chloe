package com.mysteryworlds.chloe.bukkit.user;

import java.util.UUID;

public interface EconomyUser {
  UUID getUniqueId();

  double getBalance();

  boolean hasBalance(double amount);

  double subtractBalance(double amount);

  double addBalance(double amount);
}
