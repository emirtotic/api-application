package com.covidtracker.repository;

import com.covidtracker.entity.CovidRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CovidDbRepository extends JpaRepository<CovidRecord, Long> {

    @Query("Select r from CovidRecord r where r.code = :countryCode")
    CovidRecord findAllRecordsForCountry(@Param("countryCode") String countryCode);

    @Query("Select r from CovidRecord r where r.code in ('AL', 'BA', 'BG', 'GR', 'ME', 'MK', 'TR', 'RS', 'HR')")
    List<CovidRecord> findAllRecordsFromBalkan();

    Page<CovidRecord> findAll(Pageable pageable);

    @Query("Select r from CovidRecord r where r.country = :countryName")
    CovidRecord findAllRecordsForCountryByName(@Param("countryName") String countryName);

}
