package ittp.ittp.Services;

import ittp.ittp.Repositories.FoodDeliveryClientsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FoodDeliveryClientsService {

    private final FoodDeliveryClientsRepository foodDeliveryClientsRepository;
    private final RedisTemplate<String, String> redisTemplate;

    //private static final int TOTAL_ENTRIES = 18_758_328;
    private static final int TOTAL_ENTRIES = 10000;
    private static final String PHONE_PREFIX = "998";
    private static final int BATCH_SIZE = 100000;
    private static final Random random = new Random();

    
    @PostConstruct
    public void saveEighteenMillionClientsToRedisStartup() {

        foodDeliveryClientsRepository.deleteAll();

        List<String[]> batch = new ArrayList<>();

        for (int i = 0; i < TOTAL_ENTRIES; i++) {

            System.out.println("Entry: " + i);

            String phoneNumber = generatePhoneNumber();
            String fullName = generateFullName();

            batch.add(new String[]{phoneNumber, fullName});

            if (batch.size() == BATCH_SIZE) {

                saveBatchToRedis(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {

            saveBatchToRedis(batch);
        }
    }

    private void saveBatchToRedis(List<String[]> batch) {

        Map<String, String> batchMap = new HashMap<>();

        for (String[] client : batch) {

            String phoneNumber = client[0];
            String fullName = client[1];

            batchMap.put(phoneNumber, fullName);
        }

        redisTemplate.opsForValue().multiSet(batchMap);
    }

    private String generatePhoneNumber() {

        int numberPart = 100_000_000 + random.nextInt(900_000_000);

        return PHONE_PREFIX + numberPart;
    }

    private String generateFullName() {

        return random.ints(10, 'A', 'Z' + 1)
                .limit(20)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public List<Map<String, Object>> allClients(int page, int size) {

        return foodDeliveryClientsRepository.findAll(page, size);
    }

    public Map<String, Object> findByPhone(String phone) {

        return foodDeliveryClientsRepository.findByPhone(phone);
    }
}