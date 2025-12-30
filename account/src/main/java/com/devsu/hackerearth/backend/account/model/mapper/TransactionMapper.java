package com.devsu.hackerearth.backend.account.model.mapper;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

public class TransactionMapper {
    public static TransactionDto toDto(Transaction transaction) {
        if (transaction == null)
            return null;

        TransactionDto dto = new TransactionDto();
        dto.setAccountId(transaction.getAccountId());
        dto.setAmount(transaction.getAmount());
        dto.setBalance(transaction.getBalance());
        dto.setDate(transaction.getDate());
        dto.setId(transaction.getId());
        dto.setType(transaction.getType());

        return dto;
    }

    public static Transaction toEntity(TransactionDto dto) {
        if (dto == null)
            return null;

        Transaction entity = new Transaction();
        entity.setAccountId(dto.getAccountId());
        entity.setAmount(dto.getAmount());
        entity.setBalance(dto.getBalance());
        entity.setDate(dto.getDate());
        entity.setType(dto.getType());

        return entity;
    }

    public static Transaction toEntityUpdated(Transaction entity, TransactionDto dto) {
        if (dto == null)
            return null;

        entity.setAccountId(dto.getAccountId());
        entity.setAmount(dto.getAmount());
        entity.setBalance(dto.getBalance());
        entity.setDate(dto.getDate());
        entity.setType(dto.getType());

        return entity;
    }
}
