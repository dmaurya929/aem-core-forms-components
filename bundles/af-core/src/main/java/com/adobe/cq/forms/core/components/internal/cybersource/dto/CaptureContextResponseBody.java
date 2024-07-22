package com.adobe.cq.forms.core.components.internal.cybersource.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptureContextResponseBody {

    private List<CTX> ctx;

    // Default constructor
    public CaptureContextResponseBody() {}

    // Constructor
    public CaptureContextResponseBody(List<CTX> ctx) {
        this.ctx = ctx;
    }

    // Getter and Setter
    public List<CTX> getCtx() {
        return ctx;
    }

    public void setCtx(List<CTX> ctx) {
        this.ctx = ctx;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CTX {

        private Data data;

        // Default constructor
        public CTX() {}

        // Constructor
        public CTX(Data data) {
            this.data = data;
        }

        // Getter and Setter
        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        private String clientLibrary;

        // Default constructor
        public Data() {}

        // Constructor
        public Data(String clientLibrary) {
            this.clientLibrary = clientLibrary;
        }

        // Getter and Setter
        public String getClientLibrary() {
            return clientLibrary;
        }

        public void setClientLibrary(String clientLibrary) {
            this.clientLibrary = clientLibrary;
        }
    }
}
