package ittp.ittp.Controllers;

import ittp.ittp.Models.AccountRecord;
import ittp.ittp.Services.AccountRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accountRecords")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
public class AccountRecordController {

    private final AccountRecordService accountRecordService;

    @PostMapping("/createOrUpdate")
    public ResponseEntity<?> createOrUpdateAccount(@RequestBody AccountRecord record) {

        return ResponseEntity.ok(accountRecordService.createOrUpdateAccountRecord(record));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccountRecord(@PathVariable String id) {

        return ResponseEntity.ok(accountRecordService.deleteAccountRecord(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> allAccounts(@RequestParam int page, @RequestParam int size) {

        return ResponseEntity.ok(accountRecordService.allAccountRecords(page, size));
    }

    @GetMapping
    public ResponseEntity<?> getRecordByAnyValue(@RequestParam String field, @RequestParam String value) {

        return ResponseEntity.ok(accountRecordService.findByAnyValue(field, value));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable String id) {

        System.out.println("ge");
        return ResponseEntity.ok(accountRecordService.findById(id));
    }
}