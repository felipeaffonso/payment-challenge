package br.com.fza.paymentchallenge.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseAuditEntity{

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String name;

    private BigDecimal balance;

    @Version
    private Integer version;

}