package com.Evidence_Service.entity;

import com.Evidence_Service.entity.base.BaseClass;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "record_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RecordInfo extends BaseClass {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "record_info_id")
    private String recordInfoId;

    @Column(name = "evidence_id")
    private String evidenceId;

    private String typeName;
    private String source;
    private String summary;

}
