package com.example.gp.gp_crud_backend.utilities;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Optional;

public class UserCache {
    private static final ConcurrentHashMap<String, CacheEntry> userhash_Cache = new ConcurrentHashMap<>();
    private static final long EXPIRATION_TIME = 30; // in minutes
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static volatile String latestHash;

    static {
        scheduler.scheduleAtFixedRate(UserCache::removeExpiredEntries, EXPIRATION_TIME, EXPIRATION_TIME, TimeUnit.MINUTES);
    }

    public static void addHash(String hash, String user, String ip_address) {
        userhash_Cache.put(hash, new CacheEntry(user, ip_address,System.currentTimeMillis()));
        latestHash = hash;
    }

    public static Optional<String> getUser(String hash) {
        CacheEntry entry = userhash_Cache.get(hash);
        if (entry != null && !entry.isExpired()) {
            latestHash = hash;
            return Optional.of(entry.getUser());
        }
        userhash_Cache.remove(hash);
        return Optional.empty();
    }

    public static Optional<String> getIpAddress(String hash) {
        CacheEntry entry = userhash_Cache.get(hash);
        if (entry != null && !entry.isExpired()) {
            return Optional.of(entry.getIpAddress());
        }
        userhash_Cache.remove(hash);
        return Optional.empty();
    }

    public static Optional<String> getCurrentUser() {
        return getUser(latestHash);
    }

    public static Optional<String> getCurrentHash() {
        return Optional.ofNullable(latestHash);
    }

    public static Optional<String> getCurrentIpAddress() {
        return getIpAddress(latestHash);
    }

    public static void removeHash(String hash) {
        userhash_Cache.remove(hash);
    }

    private static void removeExpiredEntries() {
        long now = System.currentTimeMillis();
        userhash_Cache.entrySet().removeIf(entry -> entry.getValue().isExpired(now));
    }

    private static class CacheEntry {
        private final String user;
        private final String ip_address;
        private final long timestamp;

        CacheEntry(String user, String ip_address,long timestamp) {
            this.user = user;
            this.ip_address = ip_address;
            this.timestamp = timestamp;
        }

        String getUser() {
            return user;
        }

        String getIpAddress() {
            return ip_address;
        }

        boolean isExpired() {
            return isExpired(System.currentTimeMillis());
        }

        boolean isExpired(long currentTime) {
            return currentTime - timestamp > TimeUnit.MINUTES.toMillis(EXPIRATION_TIME);
        }
    }
}

