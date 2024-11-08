package es.in2.issuer.shared.config;

import es.in2.issuer.shared.repository.CacheStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CacheStoreConfigTest {
    private CacheStoreConfig cacheStoreConfig;

    @BeforeEach
    void setUp() {
        cacheStoreConfig = new CacheStoreConfig();
    }

    @Test
    void testTransactionCodeCache() {
        CacheStore<String> cacheStoreForTransactionCode = cacheStoreConfig.transactionCodeCache();
        assertNotNull(cacheStoreForTransactionCode);
    }

}