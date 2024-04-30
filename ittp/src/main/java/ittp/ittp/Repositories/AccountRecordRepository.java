package ittp.ittp.Repositories;

import ittp.ittp.Models.AccountRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRecordRepository extends CrudRepository<AccountRecord, String> {

    List<AccountRecord> findByAccount(long account);

    List<AccountRecord> findByName(String name);

    List<AccountRecord> findByValue(double value);

    List<AccountRecord> findAll();
}