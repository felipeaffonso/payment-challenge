package br.com.fza.paymentchallenge.rest.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
@ApiModel(value = "accountRequest", description = "The account request needs a name and a initialBalance value.")
public class AccountRequest {

    @NonNull
    @Length(min = 3, max = 20)
    private String name;

    @PositiveOrZero
    private BigDecimal initialBalance;

}