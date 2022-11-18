package com.snaacker.timeregister.persistent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
@RestResource(exported = false)
public class Message extends BaseObject {
    @Column(name = "title")
    String title;

    @Column(name = "content")
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User users;
}
