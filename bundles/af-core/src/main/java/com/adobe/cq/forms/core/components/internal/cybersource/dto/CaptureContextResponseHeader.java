package com.adobe.cq.forms.core.components.internal.cybersource.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CaptureContextResponseHeader {
    private String kid;
    private String alg;
}