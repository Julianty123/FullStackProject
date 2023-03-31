package com.credibanco.assessment.card.service.impl;

import com.credibanco.assessment.card.TransactionRepository;
import com.credibanco.assessment.card.model.Card;
import com.credibanco.assessment.card.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PSIMPLtransaction{
    public static TransactionRepository transactionRepository;

    @Autowired
    public PSIMPLtransaction(TransactionRepository transactionRepository) {
        PSIMPLtransaction.transactionRepository = transactionRepository;
    }

    public static List<Transaction> consultarTransacciones() {
        return (List<Transaction>) transactionRepository.findAll();
    }
}
