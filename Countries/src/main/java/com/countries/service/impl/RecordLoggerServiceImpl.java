package com.countries.service.impl;

import com.countries.entity.RecordLogger;
import com.countries.repository.RecordLoggerRepository;
import com.countries.service.RecordLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordLoggerServiceImpl implements RecordLoggerService {

    @Autowired
    private RecordLoggerRepository recordLoggerRepository;

    @Override
    public void saveCovidRecord(RecordLogger recordLogger) {
        recordLoggerRepository.save(recordLogger);
    }

}
