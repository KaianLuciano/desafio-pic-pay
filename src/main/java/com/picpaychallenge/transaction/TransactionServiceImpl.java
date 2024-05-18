package com.picpaychallenge.transaction;

import com.picpaychallenge.common.domain.config.exception.erros.Exception;
import com.picpaychallenge.transaction.payload.TransferDTO;
import com.picpaychallenge.user.User;
import com.picpaychallenge.user.payload.TypeUser;
import com.picpaychallenge.user.payload.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Value("${mock.notification.url}")
    private String notificationServiceUrl;
    @Value("${mock.authorization.url}")
    private String authorizationServiceUrl;


    @Override
    @Transactional
    public void transfer(TransferDTO transferDTO) {
        User payer = userRepository.findById(transferDTO.payerId()).orElseThrow(Exception.UserNotFoundException::new);
        User payee = userRepository.findById(transferDTO.payeeId()).orElseThrow(Exception.UserNotFoundException::new);

        if (payer.getTypeUser().equals(TypeUser.SHOPKEEPER)) {
            throw new Exception.MerchantNotAllowedException();
        }

        if (payer.getBalance().compareTo(transferDTO.value()) < 0) {
            throw new Exception.NotEnoughBalanceException();
        }

        payer.setBalance(payer.getBalance().subtract(transferDTO.value()));
        payee.setBalance(payee.getBalance().add(transferDTO.value()));

        userRepository.save(payer);
        userRepository.save(payee);

        Transaction transaction = new Transaction(transferDTO.payerId(), transferDTO.payeeId(), transferDTO.value());

        authorizeTransaction();
        notifyUser(transaction);

        transactionRepository.save(transaction);
        log.info("Transaction saved: {}", transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    private void notifyUser(Transaction transaction) {
        log.info("Checking notification service...");

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(notificationServiceUrl, String.class);

        if (responseEntity.getStatusCode().value() == 200) {
            log.info("Response from notification service: " + transaction.toString());
        } else {
            throw new Exception.FailedNotificationException();
        }
    }

    public void authorizeTransaction() {
        try {
            ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(authorizationServiceUrl, String.class);

            if (responseEntity.getStatusCode().value() == 200) {
                log.info("Transaction authorized.");
            } else {
                throw new Exception.AuthorizationException();
            }
        } catch (RestClientException e) {
            throw new Exception.AuthorizationException();
        }
    }
}
