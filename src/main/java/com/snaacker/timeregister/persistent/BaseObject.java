package com.snaacker.timeregister.persistent;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

}
