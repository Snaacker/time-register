package com.snaacker.timeregister.persistent;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@MappedSuperclass
public class BaseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @Column(name = "created_date")
    protected Date createdDate;

    @Column(name = "updated_date")
    protected Date updatedDate;

    @Column(name = "description")
    protected String description;

    protected void setCreatedDate(){
        this.createdDate = new Date();
    }

    protected void setUpdatedDate(){
        this.updatedDate = new Date();
    }
}
