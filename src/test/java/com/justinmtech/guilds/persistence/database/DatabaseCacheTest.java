package com.justinmtech.guilds.persistence.database;

import com.justinmtech.guilds.service.TransactionCacheImp;
import org.junit.jupiter.api.*;

import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseCacheTest {
    private static TransactionCacheImp databaseCache;
    private static UUID UUID;

    @BeforeAll
    static void setup() {
        UUID = java.util.UUID.randomUUID();
        databaseCache = new TransactionCacheImp();
    }

    @Test
    @Order(1)
    public void addTransactionConfirmation() {
        databaseCache.addTransactionConfirmation(UUID, 25);
        Assertions.assertTrue(databaseCache.hasPendingTransaction(UUID));
    }

    @Test
    @Order(2)
    public void removeTransactionConfirmation() {
        databaseCache.removeTransactionConfirmation(UUID);
        Assertions.assertFalse(databaseCache.hasPendingTransaction(UUID));
    }
}