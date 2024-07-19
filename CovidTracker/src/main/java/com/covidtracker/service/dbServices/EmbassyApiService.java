package com.covidtracker.service.dbServices;

import com.covidtracker.dto.embassy.EmbassyApiResponse;

public interface EmbassyApiService {

    EmbassyApiResponse findYourEmbassies(String source, String destination);
}
