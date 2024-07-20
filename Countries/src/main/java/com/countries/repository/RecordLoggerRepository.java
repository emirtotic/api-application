package com.countries.repository;

import com.countries.entity.RecordLogger;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecordLoggerRepository extends MongoRepository<RecordLogger, String> {

}
