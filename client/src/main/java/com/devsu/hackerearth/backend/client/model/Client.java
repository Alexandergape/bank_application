package com.devsu.hackerearth.backend.client.model;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name="persons",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dni"})
    }
)
public class Client extends Person {
	private String password;
	private boolean isActive;
}
