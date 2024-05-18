package com.picpaychallenge.transaction;

import com.picpaychallenge.common.domain.config.exception.erros.Exception;
import com.picpaychallenge.user.payload.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

}
