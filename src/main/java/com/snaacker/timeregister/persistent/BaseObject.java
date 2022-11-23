package com.snaacker.timeregister.persistent;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@Data
@MappedSuperclass
public class BaseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @CreatedBy private Long createdBy;

    @CreatedDate
    @Column(name = "created_date")
    protected Date createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    protected Date updatedDate;

    @Column(name = "description")
    protected String description;
}
