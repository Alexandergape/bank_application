package com.devsu.hackerearth.backend.account.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name="accounts",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"client_id", "number"})
    }
)
public class Account extends Base {
    private String number;
	private String type;
	private double initialAmount;
	private boolean isActive;

    @Column(name = "client_id")
    private Long clientId;
}
