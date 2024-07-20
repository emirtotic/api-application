package com.countries.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "covid-record")
public class CovidRecord {

    @Id
    private String id;
    private Long thirdPartyId;
    private String country;
    private String code;
    private Long confirmed;
    private Long recovered;
    private Long critical;
    private Long deaths;
    private Date lastChange;
    private Date lastUpdate;

}
