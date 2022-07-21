package com.justinmtech.guilds.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cache {
    private final Map<UUID, Double> transactionConfirmations;

    public Cache() {
        this.transactionConfirmations = new HashMap<>();
    }

    public Map<UUID, Double> getTransactionConfirmations() {
        return transactionConfirmations;
    }

    public void addTransactionConfirmation(UUID uuid, double value) {
        transactionConfirmations.put(uuid, value);
    }

    public void removeTransactionConfirmation(UUID uuid) {
        transactionConfirmations.remove(uuid);
    }

    public boolean hasPendingTransaction(UUID uuid) {
        return transactionConfirmations.containsKey(uuid);
    }
}
