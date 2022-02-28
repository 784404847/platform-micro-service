package com.mservice.common.util.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wuwenjun
 * @date 2019/9/10 0010 12:31
 * @desc redis操作工具类（目前只写了 string 和hash 的数据结构，后续需要其他的可再添加）
 */
@Slf4j
public class RedisUtil<K, V, HK, HV> {

    private final RedisTemplate<K, V> redisTemplate;

    public RedisUtil(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * redis设置值
     *
     * @param key
     * @param value
     * @param time     失效时间
     * @param timeUnit 单位
     * @return
     */
    public void setKV(K key, V value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue()
                .set(key, value, time, timeUnit);
    }


    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key
     * @param value
     * @param time     失效时间
     * @param timeUnit 单位
     * @return
     */
    public Boolean setIfAbsent(K key, V value, long time, TimeUnit timeUnit) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(key, value, time, timeUnit);
    }


    /**
     * 获取指定 key 的值
     *
     * @param key
     * @return
     */
    public V get(K key) {
        return redisTemplate
                .opsForValue()
                .get(key);
    }


    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     *
     * @param key
     * @param value
     * @return
     */
    public V getAndSet(K key, V value) {
        return redisTemplate
                .opsForValue()
                .getAndSet(key, value);
    }

    /**
     * 批量获取值
     *
     * @param keys
     * @return
     */
    public List<V> multiGet(Collection<K> keys) {
        return redisTemplate
                .opsForValue()
                .multiGet(keys);
    }

    /**
     * 批量删除key
     *
     * @param keys
     */
    public void deleteKeys(Set<K> keys) {
        redisTemplate.delete(keys);
    }


    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    public Boolean hasKey(K key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param timeout
     * @param unit
     */
    public void expire(K key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中
     *
     * @param key
     * @param dbIndex
     * @return
     */
    public Boolean moveToDbIndex(K key, Integer dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 增加(默认1)
     *
     * @param key
     * @return
     */
    public Long incrBy(K key, long time, TimeUnit timeUnit) {
        return incrBy(key, 1, time, timeUnit);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key
     * @param increment
     * @return
     */
    public Long incrBy(K key, long increment, long time, TimeUnit timeUnit) {

        boolean hasKey = hasKey(key);
        Long value = redisTemplate
                .opsForValue()
                .increment(key, increment);
        if (!hasKey) {
            expire(key, time, timeUnit);
        }
        return value;
    }

    /**
     * 增加(自增长), 负数则为自减  浮点数
     *
     * @param key
     * @param increment
     * @return
     */
    public Double incrByFloat(K key, double increment, long time, TimeUnit timeUnit) {

        boolean hasKey = hasKey(key);
        Double value = redisTemplate
                .opsForValue()
                .increment(key, increment);
        if (!hasKey) {
            expire(key, time, timeUnit);
        }
        return value;
    }

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key
     * @param hashKey
     * @return
     */
    public HV hGet(K key, HK hashKey) {
        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
        return operations.get(key, hashKey);
    }


    /**
     * 获取所有给定字段的值
     *
     * @param key
     * @return
     */
    public Map<HK, HV> hGetAll(K key) {
        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
        return operations.entries(key);
    }


    /**
     * 获取所有给定字段的值
     *
     * @param key
     * @param fields
     * @return
     */
    public List<HV> hMultiGet(K key, Collection<HK> fields) {
        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
        return operations.multiGet(key, fields);
    }

    /**
     * @param key
     * @param hashKey
     * @param value
     * @param time
     */
    public void hPut(K key, HK hashKey, HV value, long time, TimeUnit timeUnit) {
        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
        operations.put(key, hashKey, value);
        if (time > 0) {
            expire(key, time, timeUnit);
        }
    }

    /**
     * hash put 并设置hash的失效时间
     *
     * @param key
     * @param maps
     * @param time
     */
    public void hPutAll(K key, Map<HK, HV> maps, long time, TimeUnit timeUnit) {

        if (maps == null || maps.isEmpty()) {
            return;
        }
        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
        operations.putAll(key, maps);
        expire(key, time, timeUnit);
    }


    /**
     * 删除一个或多个哈希表字段
     *
     * @param key
     * @param fields
     * @return
     */
    public void hDelete(K key, Set<HK> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
        operations.delete(key, fields.toArray());
    }


    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key
     * @param field
     * @return
     */
    public boolean hExists(K key, HK field) {
        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
        return operations.hasKey(key, field);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key
     * @param field
     * @param increment
     * @return
     */
    public Long hIncrBy(K key, HK field, long increment, long time, TimeUnit timeUnit) {
        boolean hasKey = hasKey(key);
        Long value = redisTemplate
                .opsForHash()
                .increment(key, field, increment);
        if (!hasKey) {
            expire(key, time, timeUnit);
        }
        return value;
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key
     * @param field
     * @param delta
     * @return
     */
    public Double hIncrByFloat(K key, HK field, double delta, long time, TimeUnit timeUnit) {
        boolean hasKey = hasKey(key);
        Double value = redisTemplate
                .opsForHash()
                .increment(key, field, delta);
        if (!hasKey) {
            expire(key, time, timeUnit);
        }
        return value;
    }


    /**
     * 获取所有哈希表中的字段
     *
     * @param key
     * @return
     */
    public Set<HK> hKeys(K key) {
        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
        return operations
                .keys(key);
    }


    /**
     * 获取哈希表中所有值
     *
     * @param key
     * @return
     */
    public List<HV> hValues(K key) {
        HashOperations<K, HK, HV> operations = redisTemplate.opsForHash();
        return operations
                .values(key);
    }

}
