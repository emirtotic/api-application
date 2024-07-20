package com.countries.repository;

import com.countries.entity.CovidRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CovidRecordRepository extends MongoRepository<CovidRecord, String> {
    Optional<CovidRecord> findCovidRecordByThirdPartyIdAndCountry(Long thirdPartyId, String country);
}
