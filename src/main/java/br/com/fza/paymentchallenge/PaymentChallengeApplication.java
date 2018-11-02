package br.com.fza.paymentchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PaymentChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentChallengeApplication.class, args);
    }

}