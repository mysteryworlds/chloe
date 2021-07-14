package com.mysteryworlds.chloe.bukkit.bank;

import java.util.List;

public interface EconomyBankRepository {
  EconomyBank findBank(String name);

  List<EconomyBank> findAll();
}
