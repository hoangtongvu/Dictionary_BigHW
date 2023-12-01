package User;

import Interfaces.Dao;

import java.util.List;
import java.util.Optional;

public class DailyRecordDAO implements Dao<DailyRecordDAO> {
    @Override
    public Optional get(String id) {
        return Optional.empty();
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public void delete(DailyRecordDAO dailyRecordDAO) {

    }

    @Override
    public void update(DailyRecordDAO dailyRecordDAO, String[] params) {

    }

    @Override
    public void save(DailyRecordDAO dailyRecordDAO) {

    }
}
