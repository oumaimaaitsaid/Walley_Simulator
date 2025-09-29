package com.crypto.service;

import java.util.*;
import java.util.stream.Collectors;

import com.crypto.model.Transaction;
import com.crypto.model.Enum.Priority;
import com.crypto.model.Enum.Status;
import com.crypto.utils.IdGenerator;

public class MempoolService {

	private final TransactionService transactionService;
	private final List<Transaction> pendingTrans;

	public MempoolService(TransactionService transactionService) {
		this.transactionService = transactionService;
		this.pendingTrans = new ArrayList<>();
	}

	

	
	
	

	
	

	

	
}
