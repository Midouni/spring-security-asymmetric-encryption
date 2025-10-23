package com.midouni.app.common;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)

public class BaseEntity {


    @CreatedBy
    @Column(name = "CREATE_BY", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "UPDATED_BY", insertable = false)
    private String updatedBy;

    @CreatedBy
    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "UPDATED_DATE", insertable = false)
    private LocalDate updatedDate;

}
