package ittp.ittp.Repositories;

import ittp.ittp.Models.FoodDeliveryClients;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodDeliveryClientsRepository extends CrudRepository<FoodDeliveryClients, String> {

    FoodDeliveryClients findFirstByPhone(String phone);
}