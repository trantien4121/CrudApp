package common;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisCacheManager {

    static String redisHost = (String) ReadPropertiesLibrary.readFileProperties().get("redisHost");
    static int redisPort = Integer.parseInt((String) ReadPropertiesLibrary.readFileProperties().get("redisPort"));
    static final int DEFAULT_EXPIRE_TIME = 3600;

    public static Jedis getJedis() {
        return new Jedis(redisHost, redisPort);
    }

    public static void setCache(String key, String value) {
        Jedis jedis = getJedis();
        jedis.set(key, value);
        jedis.expire(key, DEFAULT_EXPIRE_TIME);
        jedis.close();
    }

    public static void setCache(String key, String value, int expireTime) {
        Jedis jedis = getJedis();
        jedis.set(key, value);
        jedis.expire(key, expireTime);
        jedis.close();
    }

    public static void setCacheAsHash(String hashName, Map<String, String> values) {
        Jedis jedis = getJedis();
        jedis.hmset(hashName, values);
        jedis.expire(hashName, DEFAULT_EXPIRE_TIME);
        jedis.close();
    }

    public static void setCacheAsSortedSet(String sortedSetName, int timeNumber, String value) {
        Jedis jedis = getJedis();
        jedis.zadd(sortedSetName, timeNumber, value);
        jedis.expire(sortedSetName, DEFAULT_EXPIRE_TIME);
        jedis.close();
    }

    public static void updateCacheAsSortedSet(String sortedSetName, String value) {
        List<String> sortedSetCache = getAllCacheAsSortedSet(sortedSetName);
        if (sortedSetCache.size() == 0 && !value.equals("")) {
            setCacheAsSortedSet(sortedSetName, 1, value);
        } else if (sortedSetCache.size() != 0 && !value.equals("")) {
            if (!sortedSetCache.contains(value)) {
                setCacheAsSortedSet(sortedSetName, 1, value);
            } else {
                for (String item : sortedSetCache) {
                    if (item.equals(value)) {
                        double score = getScoreOfSortedCache(sortedSetName, item);
                        setCacheAsSortedSet(sortedSetName, (int) score + 1, item);
                    }
                }
            }
        }

    }

    public static String getCache(String key) {
        Jedis jedis = getJedis();
        return jedis.get(key);
    }

    public static Map<String, String> getCacheAsHash(String hashName) {
        Jedis jedis = getJedis();
        Map<String, String> values = jedis.hgetAll(hashName);
        jedis.close();
        return values;
    }

    public static List<String> getAllCacheAsSortedSet(String sortedSetName) {
        Jedis jedis = getJedis();
        List<String> values = jedis.zrange(sortedSetName, 0, -1);
        jedis.close();
        return values;

    }

    public static List<String> getPopularCacheAsSortedSet(String sortedSetName, int count) {
        Jedis jedis = getJedis();
        List<String> values = jedis.zrevrange(sortedSetName, 0, count - 1);
        jedis.close();
        return values;
    }

    public static Double getScoreOfSortedCache(String sortedSetName, String member) {
        Jedis jedis = getJedis();
        Double score = jedis.zscore(sortedSetName, member);
        return score;
    }

    public static void delCache(String key) {
        Jedis jedis = getJedis();
        jedis.del(key);
        jedis.close();
    }

}

