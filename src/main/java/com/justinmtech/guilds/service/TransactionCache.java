package com.justinmtech.guilds.service;

import java.util.UUID;

public interface TransactionCache {

    void addTransactionConfirmation(UUID uuid, double value);
    Double getPendingTransactionAmount(UUID uuid);
    void removeTransactionConfirmation(UUID uuid);
    boolean hasPendingTransaction(UUID uuid);

}

