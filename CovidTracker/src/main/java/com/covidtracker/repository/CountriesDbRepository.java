package com.covidtracker.repository;

import com.covidtracker.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CountriesDbRepository extends JpaRepository<Country, Long> {

    @Query("Select c from Country c where c.name = :name")
    Country findByName(@Param("name") String name);

}
