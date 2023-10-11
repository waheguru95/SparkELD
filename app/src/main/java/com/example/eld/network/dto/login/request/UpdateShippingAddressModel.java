package com.example.eld.network.dto.login.request;

public class UpdateShippingAddressModel {
        private String id;
        private String shippingAddress;

        public UpdateShippingAddressModel(String id, String shippingAddress) {
            this.id = id;
            this.shippingAddress = shippingAddress;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
        }
    }
