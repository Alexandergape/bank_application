package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.exception.EntityAlreadyExistsException;
import com.devsu.hackerearth.backend.account.exception.NotFoundException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.model.mapper.AccountMapper;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDto> getAll() {
        // Get all accounts
        return accountRepository.findAll().stream().map(AccountMapper::toDto).collect(Collectors.toList());
    }

    private Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account", id));
    }

    @Override
    public AccountDto getById(Long id) {
        // Get accounts by id
        Account account = getAccountById(id);
        return AccountMapper.toDto(account);
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        Account account = AccountMapper.toEntity(accountDto);
        try {
            accountRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyExistsException("Account", "number, client",
                    accountDto.getNumber() + ", " + accountDto.getClientId());
        }
        return AccountMapper.toDto(account);
    }

    @Override
    @Transactional
    public AccountDto update(AccountDto accountDto) {
        Account account = getAccountById(accountDto.getId());
        AccountMapper.toEntityUpdated(account, accountDto);

        try {
            // accountRepository.saveAndFlush(account);
            accountRepository.save(account);
            accountRepository.flush();
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new EntityAlreadyExistsException("Account", "number, client",
                    accountDto.getNumber() + ", " + accountDto.getClientId());
        }

        return AccountMapper.toDto(account);
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        // Partial update account
        Account account = getAccountById(id);

        account.setActive(partialAccountDto.isActive());
        accountRepository.save(account);
        return AccountMapper.toDto(account);
    }

    @Override
    public void deleteById(Long id) {
        // Delete account
        if (!accountRepository.existsById(id))
            throw new RuntimeException("Account with id: " + id + " not found");

        accountRepository.deleteById(id);
    }

}
