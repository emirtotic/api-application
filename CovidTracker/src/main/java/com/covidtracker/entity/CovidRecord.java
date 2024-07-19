package com.covidtracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "covid_record", schema = "covid-tracker")
public class CovidRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String country;
    private Long confirmed;
    private Long recovered;
    private Long critical;
    private Long deaths;
    @Column(name = "last_change")
    private Date lastChange;
    @Column(name = "last_update")
    private Date lastUpdate;

}
