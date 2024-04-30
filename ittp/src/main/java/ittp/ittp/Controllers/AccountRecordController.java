package ittp.ittp.Controllers;

import ittp.ittp.Models.AccountRecord;
import ittp.ittp.Services.AccountRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> allAccounts() {

        return ResponseEntity.ok(accountRecordService.allAccountRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountRecord> getRecordById(@PathVariable String id) {

        AccountRecord record = accountRecordService.findById(id);

        if (record != null) {

            return ResponseEntity.ok(record);
        }
        else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/account/{account}")
    public ResponseEntity<List<AccountRecord>> getRecordsByAccount(@PathVariable Long account) {

        return ResponseEntity.ok(accountRecordService.findByAccount(account));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<AccountRecord>> getRecordsByName(@PathVariable String name) {

        return ResponseEntity.ok(accountRecordService.findByName(name));
    }

    @GetMapping("/value/{value}")
    public ResponseEntity<List<AccountRecord>> getRecordsByValue(@PathVariable Double value) {

        return ResponseEntity.ok(accountRecordService.findByValue(value));
    }
}