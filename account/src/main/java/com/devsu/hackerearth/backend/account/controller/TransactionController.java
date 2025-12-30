package com.devsu.hackerearth.backend.account.controller;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping
	public ResponseEntity<List<TransactionDto>> getAll() {
		return ResponseEntity.ok(transactionService.getAll());
	}

	@GetMapping("{id}")
	public ResponseEntity<TransactionDto> get(@PathVariable Long id) {
		// return ResponseEntity.ok(transactionService.getById(id));
		var res = transactionService.getById(id);
		if (res != null)
			return ResponseEntity.ok(res);
		else
			return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<TransactionDto> create(@RequestBody TransactionDto transactionDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(transactionDto));
	}

	@GetMapping("clients/{clientId}/report")
	public ResponseEntity<List<BankStatementDto>> report(@PathVariable Long clientId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionStart,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionEnd) {
		return ResponseEntity.ok(transactionService.getAllByAccountClientIdAndDateBetween(clientId,
				dateTransactionStart, dateTransactionEnd));
	}

	@GetMapping("clients/{clientId}/report/page")
	public ResponseEntity<Page<BankStatementDto>> report(@PathVariable Long clientId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionStart,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionEnd,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size
		) {
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(transactionService.getAllByAccountClientIdAndDateBetween(clientId,
				dateTransactionStart, dateTransactionEnd, pageable));
	}
}
