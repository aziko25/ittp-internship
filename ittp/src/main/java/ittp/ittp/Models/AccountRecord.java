package ittp.ittp.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("AccountRecord")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRecord {

    @Id
    private String id;

    @Indexed
    private Long account;

    @Indexed
    private String name;

    @Indexed
    private Double value;
}