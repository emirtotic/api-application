package com.countries.repository;

import com.countries.entity.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CountryRepository extends MongoRepository<Country, String> {
    Optional<Country> findCountryByName(String name);
}
