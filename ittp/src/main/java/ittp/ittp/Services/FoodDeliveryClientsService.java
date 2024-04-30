package ittp.ittp.Services;

import ittp.ittp.Models.FoodDeliveryClients;
import ittp.ittp.Repositories.FoodDeliveryClientsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodDeliveryClientsService {

    private final FoodDeliveryClientsRepository foodDeliveryClientsRepository;

    public FoodDeliveryClients findByPhone(String phone) {

        return foodDeliveryClientsRepository.findFirstByPhone(phone);
    }
}