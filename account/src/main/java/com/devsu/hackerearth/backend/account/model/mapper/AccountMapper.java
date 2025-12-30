package com.devsu.hackerearth.backend.account.model.mapper;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;

public class AccountMapper {

    public static AccountDto toDto(Account account) {
        if (account == null)
            return null;

        AccountDto dto = new AccountDto();
        dto.setActive(account.isActive());
        dto.setClientId(account.getClientId());
        dto.setId(account.getId());
        dto.setInitialAmount(account.getInitialAmount());
        dto.setNumber(account.getNumber());
        dto.setType(account.getType());

        return dto;
    }

    public static Account toEntity(AccountDto dto) {
        if (dto == null)
            return null;

        Account entity = new Account();
        entity.setActive(dto.isActive());
        entity.setClientId(dto.getClientId());
        entity.setInitialAmount(dto.getInitialAmount());
        entity.setNumber(dto.getNumber());
        entity.setType(dto.getType());

        return entity;
    }

    public static Account toEntityUpdated(Account entity, AccountDto dto) {
        if (dto == null)
            return null;

        entity.setActive(dto.isActive());
        entity.setClientId(dto.getClientId());
        entity.setInitialAmount(dto.getInitialAmount());
        entity.setNumber(dto.getNumber());
        entity.setType(dto.getType());

        return entity;
    }
}
