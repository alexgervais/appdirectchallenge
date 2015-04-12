package org.example.appdirect.service.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "event")
public class EventDTO {

    private String type;
    private MarketplaceDTO marketplace;
    private String flag;
    private CreatorDTO creator;
    private PayloadDTO payload;
    private String returnUrl;

    public String getType() {

        return type;
    }

    public void setType(final String type) {

        this.type = type;
    }

    public MarketplaceDTO getMarketplace() {

        return marketplace;
    }

    public void setMarketplace(final MarketplaceDTO marketplace) {

        this.marketplace = marketplace;
    }

    public String getFlag() {

        return flag;
    }

    public void setFlag(final String flag) {

        this.flag = flag;
    }

    public CreatorDTO getCreator() {

        return creator;
    }

    public void setCreator(final CreatorDTO creator) {

        this.creator = creator;
    }

    public PayloadDTO getPayload() {

        return payload;
    }

    public void setPayload(final PayloadDTO payload) {

        this.payload = payload;
    }

    public String getReturnUrl() {

        return returnUrl;
    }

    public void setReturnUrl(final String returnUrl) {

        this.returnUrl = returnUrl;
    }
}
