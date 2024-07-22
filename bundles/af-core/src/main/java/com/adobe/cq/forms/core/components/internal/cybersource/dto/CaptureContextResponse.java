package com.adobe.cq.forms.core.components.internal.cybersource.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CaptureContextResponse {

    private String captureContextJwt;
    private String decodedBody;
    private String clientVersion;
}
