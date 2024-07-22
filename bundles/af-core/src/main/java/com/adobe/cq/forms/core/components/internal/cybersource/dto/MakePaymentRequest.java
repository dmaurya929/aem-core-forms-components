package com.adobe.cq.forms.core.components.internal.cybersource.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MakePaymentRequest {

    private String clientReferenceCode;
    private String cardholderName;
    private String transientToken;
    private String amount;
    private String currency;
}
