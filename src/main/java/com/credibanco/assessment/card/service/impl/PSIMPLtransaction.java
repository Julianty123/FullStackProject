package com.credibanco.assessment.card.service.impl;

import com.credibanco.assessment.card.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PSIMPLtransaction{
    public static TransactionRepository transactionRepository;

    @Autowired
    public PSIMPLtransaction(TransactionRepository transactionRepository) {
        PSIMPLtransaction.transactionRepository = transactionRepository;
    }
}
