package com.mysteryworlds.chloe.bukkit.user;

import java.util.Optional;
import java.util.UUID;

public interface EconomyUserRepository {

  void save(EconomyUser user);

  Optional<EconomyUser> findUser(UUID uniqueId);
}
