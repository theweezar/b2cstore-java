package com.ecom.b2cstore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.ecom.b2cstore.entity.Container;
import com.ecom.b2cstore.entity.Product;
import lombok.Getter;
import lombok.Setter;

public abstract class ContainerModel {
    class Total {
        @Getter
        @Setter
        private double totalPrice;

        @Getter
        @Setter
        private double totalTax;

        @Getter
        @Setter
        private double totalDiscount;

        @Getter
        @Setter
        private double totalShipping;

        public Total() {
            this.totalPrice = 0;
            this.totalTax = 0;
            this.totalDiscount = 0;
            this.totalShipping = 0;
        }
    }

    class Shipping {
        @Getter
        @Setter
        private String firstName;

        @Getter
        @Setter
        private String lastName;

        @Getter
        @Setter
        private String phone;

        @Getter
        @Setter
        private AddressModel address;

        public Shipping() {
            this.firstName = "";
            this.lastName = "";
            this.phone = "";
            this.address = new AddressModel();
        }
    }

    class Billing {
        @Getter
        @Setter
        private String firstName;

        @Getter
        @Setter
        private String lastName;

        @Getter
        @Setter
        private String email;

        @Getter
        @Setter
        private AddressModel address;

        public Billing() {
            this.firstName = "";
            this.lastName = "";
            this.email = "";
            this.address = new AddressModel();
        }
    }

    @Getter
    @Setter
    public Total total;

    @Getter
    @Setter
    private int itemCount;

    @Getter
    @Setter
    private Shipping shipping;

    @Getter
    @Setter
    private Billing billing;

    @Getter
    @Setter
    private List<LineItemModel> items = new ArrayList<>();

    protected abstract Container getContainerInstance();

    public ContainerModel() {
        this.total = new Total();
        this.itemCount = 0;
        this.shipping = new Shipping();
        this.billing = new Billing();
    }

    public void copyShippingFrom(Container container) {
        shipping.setFirstName(container.getShipFirstName());
        shipping.setLastName(container.getShipLastName());
        shipping.setPhone(container.getPhone());
        shipping.getAddress().copy(container);
    }

    public void copyBillingFrom(Container container) {
        billing.setFirstName(container.getFirstName());
        billing.setLastName(container.getLastName());
        billing.setEmail(container.getEmail());
        billing.getAddress().copy(container);
    }

    public List<LineItemModel> createItemList(Map<String, Product> productMap) {
        List<LineItemModel> lineItemList = new ArrayList<>();
        Container container = getContainerInstance();
        if (container != null && productMap != null) {
            container.getContainerLineItems().forEach(lineItem -> {
                Product product = productMap.get(lineItem.getProductId());
                if (product != null) {
                    LineItemModel lineItemModel = new LineItemModel(lineItem);
                    lineItemModel.parseProduct(product);
                    lineItemList.add(lineItemModel);
                }
            });
        }
        return lineItemList;
    }
}
