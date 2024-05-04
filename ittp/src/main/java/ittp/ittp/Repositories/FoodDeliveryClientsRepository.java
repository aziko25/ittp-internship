package ittp.ittp.Repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class FoodDeliveryClientsRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void deleteAll() {

        Set<String> keys = redisTemplate.keys("*");

        if (keys != null && !keys.isEmpty()) {

            redisTemplate.delete(keys);
        }
    }

    public List<Map<String, Object>> findAll(int page, int size) {

        return getCursorsAndPagination(page, size, redisTemplate);
    }

    static List<Map<String, Object>> getCursorsAndPagination(int page, int size, RedisTemplate<String, Object> redisTemplate) {

        List<Map<String, Object>> paginatedResults = new ArrayList<>();

        try (Cursor<byte[]> cursor = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection()
                .scan(ScanOptions.scanOptions().count(size).match("*").build())) { // Fetch all keys

            int start = page * size;
            int end = start + size;
            int currentIndex = 0;

            while (cursor.hasNext() && currentIndex < end) {

                byte[] key = cursor.next();

                String keyStr = new String(key, StandardCharsets.UTF_8);

                if (!keyStr.startsWith("record:")) {

                    if (currentIndex >= start) {

                        Object value;

                        try {

                            value = redisTemplate.opsForValue().get(keyStr);
                        }
                        catch (Exception e) {

                            value = "Error fetching value: " + e.getMessage();
                        }

                        Map<String, Object> record = new HashMap<>();

                        record.put(keyStr, value);
                        paginatedResults.add(record);
                    }
                }

                currentIndex++;
            }
        }

        return paginatedResults;
    }

    public Map<String, Object> findByPhone(String phone) {

        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();

        Object value = valueOps.get(phone);

        Map<String, Object> result = new HashMap<>();

        if (value != null) {

            result.put(phone, value);
        }

        return result;
    }
}