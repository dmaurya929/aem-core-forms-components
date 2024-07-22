/*******************************************************************************
 * Copyright 2023 Adobe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

(function () {

    class CheckoutCybersource extends FormView.FormPanel {
        static NS = FormView.Constants.NS;
        static IS = "adaptiveFormCheckoutCybersource";
        static bemBlock = 'cmp-adaptiveform-checkoutcybersource';
        static selectors = {
            self: "[data-" + this.NS + '-is="' + this.IS + '"]',
            label: `.${CheckoutCybersource.bemBlock}__label`,
            description: `.${CheckoutCybersource.bemBlock}__longdescription`,
            qm: `.${CheckoutCybersource.bemBlock}__questionmark`,
            tooltipDiv: `.${CheckoutCybersource.bemBlock}__shortdescription`,
            ccContentWrapper: `.${CheckoutCybersource.bemBlock}__content-container`,
            microform: `.${CheckoutCybersource.bemBlock}__microform`,
            checkoutButton: `.${CheckoutCybersource.bemBlock}__checkout-button`,
            payandsubmitButton: `.${CheckoutCybersource.bemBlock}__payandsubmit button`,
            itemTotal: `.${CheckoutCybersource.bemBlock}__itemtotal input`
        };

        microformStyle = {
            'input': {
                'font-size': '14px',
                'font-family': 'helvetica, tahoma, calibri, sans-serif',
                'color': '#555'
            },
            ':focus': { 'color': 'blue' },
            ':disabled': { 'cursor': 'not-allowed' },
            'valid': { 'color': '#3c763d' },
            'invalid': { 'color': '#a94442' }
        };

        constructor(params) {
            super(params);
            this.setupElement();
        }

        async loadMicroform() {
            this.element.parentElement.style.position = "absolute";
            this.element.parentElement.style.width = "100%";
            this.element.parentElement.style.height = "100%";
            this.element.parentElement.style.backgroundColor = "white";
            this.element.parentElement.style.zIndex = 1000;
            this.element.parentElement.style.marginTop = "50px";
            this.getMicroform().style.display = "block";
            this._model.items.find(m => m.name === "checkout").visible = false;
            const captureContext = await fetch("http://localhost:8080/api/capture-context", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "targetOrigins": ["http://localhost:8080", "http://localhost:4502"],
                    "allowedCardNetworks": ["VISA", "MASTERCARD", "AMEX"],
                    "clientVersion": "v2"
                })
            });
            const resp = await captureContext.json();

            // load a client js library from resp.clientVersion
            const script = document.createElement('script');
            script.src = resp.clientVersion;
            script.onload = this.renderMicroform.bind(this, resp);
            document.body.appendChild(script);
        }

        setupElement() {
            this.getCheckoutButton().addEventListener('click', this.loadMicroform.bind(this));
            this.getMicroform().style.display = "none";
        }

        renderMicroform(resp) {
            var flex = new Flex(resp.captureContextJwt);
            var microform = flex.microform({ styles: this.microformStyle });
            var number = microform.createField('number', { placeholder: 'Enter card number' });
            var securityCode = microform.createField('securityCode', { placeholder: '•••' });
            number.load('#number-container');
            securityCode.load('#securityCode-container');

            const  payButton = this.element.querySelector('#pay-button');
            const cardholderName = this.element.querySelector('#cardholderName');
            const expMonth = this.element.querySelector('#expMonth');
            const expYear = this.element.querySelector('#expYear');

            const cancelButton = this.element.querySelector('.action-buttons .cancel');

            const that = this;
            payButton.addEventListener('click', function() {
                console.log(that.microformStyle);
                var options = {
                    expirationMonth: expMonth.value,
                    expirationYear: expYear.value
                };

                microform.createToken(options, async function (err, token) {
                    if (err) {
                        console.error(err);
                    } else {
                        const itemTotal = that.getItemTotal().value;
                        const paymentResponse = await fetch("http://localhost:8080/api/payment", {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                cardholderName: cardholderName.value || "John Doe",
                                amount: itemTotal || 0,
                                currency: "USD",
                                transientToken: token,
                                clientReferenceCode: "test_payment"
                            })
                        });
                        if(paymentResponse.ok) {
                            that._model.items.find(m => m.name === "submit").dispatch(new FormView.Actions.Click())
                        } else {
                            console.error("Payment failed");
                            alert("Payment failed");
                        }
                    }
                });
            });

            cancelButton.addEventListener('click', function() {
                that.element.parentElement.style.position = "inherit";
                that.element.parentElement.style.width = "inherit";
                that.element.parentElement.style.height = "inherit";
                that.element.parentElement.style.zIndex = 0;
                that.getMicroform().style.display = "none";
                that._model.items.find(m => m.name === "checkout").visible = true;
            })
        }


        addChild(childView) {
            super.addChild(childView);
        }

        getContentDivWrapper() {
            return this.element.querySelector(CheckoutCybersource.selectors.ccContentWrapper);
        }

        getCheckoutButton() {
            return this.element.querySelector(CheckoutCybersource.selectors.checkoutButton);
        }

        getPayAndSubmitButton() {
            return this.element.querySelector(CheckoutCybersource.selectors.payandsubmitButton);
        }

        getMicroform() {
            return this.element.querySelector(CheckoutCybersource.selectors.microform);
        }

        getItemTotal() {
            return this.element.querySelector(CheckoutCybersource.selectors.itemTotal);
        }

        getWidget() {
            return null;
        }

        getDescription() {
            return this.element.querySelector(CheckoutCybersource.selectors.description);
        }

        getLabel() {
            return null;
        }

        getErrorDiv() {
            return null;
        }

        getTooltipDiv() {
            return this.element.querySelector(CheckoutCybersource.selectors.tooltipDiv);
        }

        getQuestionMarkDiv() {
            return this.element.querySelector(CheckoutCybersource.selectors.qm);
        }
    }

    FormView.Utils.setupField(({element, formContainer}) => {
        return new CheckoutCybersource({element, formContainer})
    }, CheckoutCybersource.selectors.self);
})();
