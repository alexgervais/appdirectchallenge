package org.example.appdirect.service.dto;

import java.util.List;

public class OrderDTO {

    private String editionCode;
    private String pricingDuration;
    private List<ItemDTO> item;

    public String getEditionCode() {

        return editionCode;
    }

    public void setEditionCode(final String editionCode) {

        this.editionCode = editionCode;
    }

    public String getPricingDuration() {

        return pricingDuration;
    }

    public void setPricingDuration(final String pricingDuration) {

        this.pricingDuration = pricingDuration;
    }

    public List<ItemDTO> getItem() {

        return item;
    }

    public void setItem(final List<ItemDTO> item) {

        this.item = item;
    }
}
