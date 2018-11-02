package br.com.fza.paymentchallenge.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode(callSuper = true)
public class Transfer extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private Account source;

    @ManyToOne
    @JoinColumn(name = "target_id", nullable = false)
    private Account target;

    private BigDecimal amount;

}