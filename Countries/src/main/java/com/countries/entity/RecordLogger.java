package com.countries.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "record-logger")
public class RecordLogger {

    @Id
    private String id;
    private String received;
    private String receivedFrom;
    private String status;
    private boolean updated;
    private Date createdAt;
}
