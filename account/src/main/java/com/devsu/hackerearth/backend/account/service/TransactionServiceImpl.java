package com.devsu.hackerearth.backend.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.exception.InsufficientFundsException;
import com.devsu.hackerearth.backend.account.exception.NotFoundException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.mapper.TransactionMapper;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.service.client.ClientServiceClient;
import com.devsu.hackerearth.backend.account.service.client.dto.ClientDto;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ClientServiceClient clientServiceClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository,
            ClientServiceClient clientServiceClient) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.clientServiceClient = clientServiceClient;
    }

    @Override
    public List<TransactionDto> getAll() {
        // Get all transactions
        return transactionRepository.findAll().stream().map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    private Transaction getTransactionById(Long id) {
        // Get Transaction by id
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction", id));
    }

    @Override
    public TransactionDto getById(Long id) {
        // Get transactions by id
        Transaction transaction = getTransactionById(id);

        return TransactionMapper.toDto(transaction);
    }

    @Override
    @Transactional
    public TransactionDto create(TransactionDto transactionDto) {
        // Create transaction

        // validate account exists
        Long accId = transactionDto.getAccountId();
        Account account = accountRepository.findById(accId)
                .orElseThrow(() -> new NotFoundException("Account", accId));

        // calculate the new balance
        double currBalance = account.getInitialAmount() + transactionDto.getAmount();

        if (currBalance < 0)
            throw new InsufficientFundsException();

        account.setInitialAmount(currBalance);
        accountRepository.save(account);

        transactionDto.setBalance(currBalance);
        transactionDto.setDate(new Date());

        Transaction transaction = TransactionMapper.toEntity(transactionDto);
        transactionRepository.save(transaction);
        return TransactionMapper.toDto(transaction);
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        List<Transaction> transactions = transactionRepository.findAllByAccountClientIdAndDateBetween(clientId,
                dateTransactionStart,
                dateTransactionEnd);

        ClientDto client = clientServiceClient.geClient(clientId);

        var res = new ArrayList<BankStatementDto>();

        for (Transaction t : transactions) {
            // search the account
            Account account = accountRepository.findById(t.getAccountId())
                    .orElseThrow(() -> new NotFoundException("Transaction", t.getAccountId()));

            var bankStatement = new BankStatementDto();
            bankStatement.setAccountNumber(account.getNumber());
            bankStatement.setClient(client.getName() + " / " + client.getDni());
            bankStatement.setAccountType(account.getType());
            bankStatement.setActive(account.isActive());
            bankStatement.setAmount(t.getAmount());
            bankStatement.setBalance(t.getBalance());
            bankStatement.setTransactionType(t.getType());
            bankStatement.setDate(t.getDate());
            bankStatement.setInitialAmount(account.getInitialAmount());

            res.add(bankStatement);
        }

        return res;
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it
        return null;
    }

    @Override
    public Page<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findAllByAccountClientIdAndDateBetween(clientId,
                dateTransactionStart,
                dateTransactionEnd, pageable);

        ClientDto client = clientServiceClient.geClient(clientId);

        // var res = new ArrayList<BankStatementDto>();

        // for (Transaction t : transactions) {
        //     // search the account
        //     Account account = accountRepository.findById(t.getAccountId())
        //             .orElseThrow(() -> new NotFoundException("Transaction", t.getAccountId()));

        //     var bankStatement = new BankStatementDto();
        //     bankStatement.setAccountNumber(account.getNumber());
        //     bankStatement.setClient(client.getName() + " / " + client.getDni());
        //     bankStatement.setAccountType(account.getType());
        //     bankStatement.setActive(account.isActive());
        //     bankStatement.setAmount(t.getAmount());
        //     bankStatement.setBalance(t.getBalance());
        //     bankStatement.setTransactionType(t.getType());
        //     bankStatement.setDate(t.getDate());
        //     bankStatement.setInitialAmount(account.getInitialAmount());

        //     res.add(bankStatement);
        // }

        List<BankStatementDto> res = transactions.getContent().stream().map(t-> {
            // search the account
            Account account = accountRepository.findById(t.getAccountId())
                    .orElseThrow(() -> new NotFoundException("Account", t.getAccountId()));

            var bankStatement = new BankStatementDto();
            bankStatement.setAccountNumber(account.getNumber());
            bankStatement.setClient(client.getName() + " / " + client.getDni());
            bankStatement.setAccountType(account.getType());
            bankStatement.setActive(account.isActive());
            bankStatement.setAmount(t.getAmount());
            bankStatement.setBalance(t.getBalance());
            bankStatement.setTransactionType(t.getType());
            bankStatement.setDate(t.getDate());
            bankStatement.setInitialAmount(account.getInitialAmount());

            return bankStatement;
        }).collect(Collectors.toList());

        return new PageImpl<>(res, pageable, transactions.getTotalElements());
    }

}
