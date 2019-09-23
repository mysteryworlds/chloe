package com.mysteryworlds.chloe.bukkit.user;

import java.util.UUID;

public interface EconomyUserFactory {

  EconomyUser createUser(UUID uniqueId);
}
