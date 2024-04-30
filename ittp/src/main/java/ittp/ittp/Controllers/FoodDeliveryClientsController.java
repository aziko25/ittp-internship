package ittp.ittp.Controllers;

import ittp.ittp.Services.FoodDeliveryClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foodClients")
@CrossOrigin(maxAge = 3600)
public class FoodDeliveryClientsController {

    private final FoodDeliveryClientsService foodDeliveryClientsService;

    @GetMapping("/phone/{phone}")
    public ResponseEntity<?> clientByPhone(@PathVariable String phone) {

        return ResponseEntity.ok(foodDeliveryClientsService.findByPhone(phone));
    }
}