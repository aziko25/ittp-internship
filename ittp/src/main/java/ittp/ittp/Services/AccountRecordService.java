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

        return accountRecordRepository.findById(id);
    }

    public AccountRecord findByAnyValue(String field, String value) {

        return accountRecordRepository.findByField(field, value);
    }

    public List<AccountRecord> allAccountRecords(int page, int size) {

        return accountRecordRepository.findAll(page, size);
    }
}