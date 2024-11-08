package es.in2.issuer.shared.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CacheStoreTest {

    private CacheStore<String> cacheStore;

    @BeforeEach
    void setUp() {
        // Initialize CacheStore with 1 seconds expiry time
        cacheStore = new CacheStore<>(1, SECONDS);
    }

    @Test
    void testAddAndGet() {
        // Add a value to the cache
        String key = "testKey";
        String value = "testValue";
        cacheStore.add(key, value);

        // Retrieve the value and verify it matches what was added
        String retrievedValue = cacheStore.get(key);
        assertEquals(value, retrievedValue, "The retrieved value should match the added value.");
    }

    @Test
    void testGetNonExistentKey() {
        // Try to get a non-existent key and expect an exception
        String key = "nonExistentKey";
        assertThrows(NoSuchElementException.class, () -> cacheStore.get(key),
                "Getting a non-existent key should throw NoSuchElementException.");
    }

    @Test
    void testDelete() {
        // Add a value, then delete it
        String key = "testKey";
        String value = "testValue";
        cacheStore.add(key, value);

        // Ensure the value exists
        assertNotNull(cacheStore.get(key));

        // Delete the key
        cacheStore.delete(key);

        // Ensure the key is no longer present
        assertThrows(NoSuchElementException.class, () -> cacheStore.get(key),
                "Deleted key should no longer be present in the cache.");
    }

    @Test
    void testAddNullValue() {
        // Try to add a null value
        String key = "nullKey";
        String result = cacheStore.add(key, null);

        // Verify that add() returns null for null values
        assertNull(result, "Adding a null value should return null.");
    }

    @Test
    void testExpiry() {
        String key = "expiringKey";
        String value = "expiringValue";
        cacheStore.add(key, value);

        // Verify it is available immediately
        assertEquals(value, cacheStore.get(key), "The value should be immediately available.");

        // Wait a short period before checking the expiration
        await().atMost(3, SECONDS).untilAsserted(() ->
            assertThrows(NoSuchElementException.class, () -> cacheStore.get(key),
                    "The entry should expire and throw NoSuchElementException.")
        );
    }
}