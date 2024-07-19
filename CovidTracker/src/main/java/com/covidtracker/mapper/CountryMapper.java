package com.covidtracker.mapper;

import com.covidtracker.dto.country.CountryApiResponse;
import com.covidtracker.dto.country.CountryDto;
import com.covidtracker.entity.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    Country mapFromApiResponseToDbEntity(CountryApiResponse countryApiResponse);
    CountryDto mapToDto(Country country);
    List<CountryDto> mapToDto(List<Country> countries);
}
