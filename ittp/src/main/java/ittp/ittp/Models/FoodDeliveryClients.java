package ittp.ittp.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("FoodDeliveryClients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodDeliveryClients {

    @Indexed
    private String phone;

    @Indexed
    private String fullName;
}