package ittp.ittp.Repositories;

import ittp.ittp.Models.AccountRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static ittp.ittp.Repositories.FoodDeliveryClientsRepository.getCursorsAndPagination;

@Repository
@RequiredArgsConstructor
public class AccountRecordRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public List<AccountRecord> findAll(int page, int size) {

        long start = (long) page * size;
        long end = start + size - 1;

        Set<Object> keys = redisTemplate.opsForZSet().range("recordKeys", start, end);

        List<AccountRecord> records = new ArrayList<>();

        if (keys != null && !keys.isEmpty()) {

            for (Object key : keys) {

                String keyStr = (String) key;

                Map<Object, Object> hash = redisTemplate.opsForHash().entries(keyStr);
                String id = keyStr.substring("record:".length()); // Extracting id from the key

                records.add(buildAccountRecord(id, hash));
            }

            return records;
        }

        return null;
    }

    private AccountRecord convertToAccountRecord(Map<Object, Object> hash) {

        System.out.println("here");

        AccountRecord record = new AccountRecord();
        record.setId((String) hash.get("id"));
        record.setAccount(Long.valueOf((String) hash.get("account")));
        record.setName((String) hash.get("name"));
        record.setValue(Double.valueOf((String) hash.get("value")));
        return record;
    }

    public AccountRecord findById(String id) {

        String hashKey = "record:" + id;

        Map<Object, Object> hash = redisTemplate.opsForHash().entries(hashKey);

        if (hash.isEmpty()) {

            return null;
        }

        return buildAccountRecord(id, hash);
    }

    public AccountRecord save(AccountRecord record) {

        if (record.getId() == null) {

            record.setId(UUID.randomUUID().toString());
        }

        String hashKey = "record:" + record.getId();

        Map<String, String> hashValues = new HashMap<>();

        hashValues.put("account", record.getAccount().toString());
        hashValues.put("name", record.getName());
        hashValues.put("value", record.getValue().toString());

        redisTemplate.opsForHash().putAll(hashKey, hashValues);
        redisTemplate.opsForZSet().add("recordKeys", hashKey, System.currentTimeMillis());

        return record;
    }

    public AccountRecord findByField(String field, String value) {

        Set<String> keys = redisTemplate.keys("record:*");

        if (keys != null) {

            for (String key : keys) {

                Map<Object, Object> hash = redisTemplate.opsForHash().entries(key);

                if (value.equals(hash.get(field))) {

                    return buildAccountRecord(key.split(":")[1], hash);
                }
            }
        }

        return null;
    }

    private AccountRecord buildAccountRecord(String id, Map<Object, Object> hash) {

        AccountRecord record = new AccountRecord();

        record.setId(id);
        record.setAccount(Long.parseLong((String) hash.get("account")));
        record.setName((String) hash.get("name"));
        record.setValue(Double.parseDouble((String) hash.get("value")));

        return record;
    }

    public void deleteById(String id) {

        redisTemplate.delete(id);
    }
}