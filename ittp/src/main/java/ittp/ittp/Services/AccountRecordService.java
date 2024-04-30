package ittp.ittp.Services;

import ittp.ittp.Models.AccountRecord;
import ittp.ittp.Repositories.AccountRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountRecordService {

    private final AccountRecordRepository accountRecordRepository;

    @Transactional
    public AccountRecord createOrUpdateAccountRecord(AccountRecord record) {

        return accountRecordRepository.save(record);
    }

    @Transactional
    public String deleteAccountRecord(String id) {

        accountRecordRepository.deleteById(id);

        return "You Successfully Deleted " + id;
    }

    public AccountRecord findById(String id) {

        return accountRecordRepository.findById(id).orElse(null);
    }

    public List<AccountRecord> allAccountRecords() {

        return accountRecordRepository.findAll();
    }

    public List<AccountRecord> findByAccount(Long account) {

        return accountRecordRepository.findByAccount(account);
    }

    public List<AccountRecord> findByName(String name) {

        return accountRecordRepository.findByName(name);
    }

    public List<AccountRecord> findByValue(Double value) {

        return accountRecordRepository.findByValue(value);
    }
}