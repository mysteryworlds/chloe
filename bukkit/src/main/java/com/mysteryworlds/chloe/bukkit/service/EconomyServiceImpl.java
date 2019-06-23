package com.mysteryworlds.chloe.bukkit.service;

import com.mysteryworlds.chloe.bukkit.repository.EconomyBankRepository;
import com.mysteryworlds.chloe.bukkit.repository.EconomyUserRepository;

public class EconomyServiceImpl implements EconomyService {

    private final EconomyUserRepository userRepository;
    private final EconomyBankRepository bankRepository;

    public EconomyServiceImpl(EconomyUserRepository userRepository, EconomyBankRepository bankRepository) {
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
    }
}
