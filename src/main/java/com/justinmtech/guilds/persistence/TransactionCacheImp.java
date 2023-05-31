package com.justinmtech.guilds.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransactionCacheImp implements TransactionCache {
    private final Map<UUID, Double> transactionConfirmations;

    public TransactionCacheImp() {
        this.transactionConfirmations = new HashMap<>();
    }

    public void addTransactionConfirmation(UUID uuid, double value) {
        transactionConfirmations.put(uuid, value);
    }

    public Double getPendingTransactionAmount(UUID uuid) {
        return transactionConfirmations.get(uuid);
    }

    public void removeTransactionConfirmation(UUID uuid) {
        transactionConfirmations.remove(uuid);
    }

    public boolean hasPendingTransaction(UUID uuid) {
        return transactionConfirmations.containsKey(uuid);
    }
}
