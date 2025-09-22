package com.ecom.b2cstore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.ecom.b2cstore.entity.Container;
import com.ecom.b2cstore.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ContainerModel {
    @Getter
    @Setter
    class Total {
        private double totalPrice;
        private double totalTax;
        private double totalDiscount;
        private double totalShipping;

        public Total() {
            this.totalPrice = 0;
            this.totalTax = 0;
            this.totalDiscount = 0;
            this.totalShipping = 0;
        }
    }

    public Total total;
    private int itemCount;
    private ShippingModel shipping;
    private BillingModel billing;
    private CustomerModel customer;
    private List<LineItemModel> items = new ArrayList<>();

    protected abstract Container getContainerInstance();

    public ContainerModel() {
        this.total = new Total();
        this.itemCount = 0;
        this.shipping = new ShippingModel();
        this.billing = new BillingModel();
    }

    public void copyShippingFrom(Container container) {
        if (container.getShippingAddress() != null) {
            shipping.getAddress().copy(container.getShippingAddress());
        }
    }

    public void copyBillingFrom(Container container) {
        if (container.getBillingAddress() != null) {
            billing.getAddress().copy(container.getBillingAddress());
        }
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
