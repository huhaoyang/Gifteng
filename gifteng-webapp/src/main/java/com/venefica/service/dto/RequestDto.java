/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venefica.service.dto;

import com.venefica.model.AdData;
import com.venefica.model.AdStatus;
import com.venefica.model.AdType;
import com.venefica.model.BusinessAdData;
import com.venefica.model.Request;
import com.venefica.model.RequestStatus;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author gyuszi
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestDto extends DtoBase {
    
    // out
    private Long id;
    // out
    private Long adId;
    // out
    private UserDto user;
    // out
    private Date requestedAt;
    // out
    private RequestStatus status;
    // out
    private String promoCode;
    
    // out
    private AdStatus adStatus;
    // out
    private Date adExpiresAt;
    // out
    private AdType type;
    // out
    //private ImageDto image;
    // out
    //private ImageDto imageThumbnail;
    
    // out
    private boolean accepted;
    // out
    private boolean sent;
    // out
    private boolean received;
    // out
    private boolean redeemed;
    
    // out
    private Integer numUnreadMessages;

    public RequestDto() {
    }
    
    public RequestDto(Request request, AdData adData) {
        this.id = request.getId();
        this.adId = request.getAd().getId();
        this.user = new UserDto(request.getUser());
        this.requestedAt = request.getRequestedAt();
        this.status = request.getStatus();
        this.type = request.getAd().getType();
        this.adStatus = request.getAd().getStatus();
        this.adExpiresAt = request.getAd().getExpiresAt();
        this.accepted = request.isAccepted();
        this.sent = request.isSent();
        this.received = request.isReceived();
        this.redeemed = request.isRedeemed();
        
        if ( request.getAd().isBusinessAd() ) {
            BusinessAdData businessAdData = (BusinessAdData) adData;
            if ( businessAdData.isGeneratePromoCodeForRequests() ) {
                //using the code generated for every request
                this.promoCode = request.getPromoCode();
            } else {
                //using the code set globally in the ad
                this.promoCode = businessAdData.getPromoCode();
            }
        }
        
        //if (request.getAd().getAdData().getMainImage() != null) {
        //    this.image = new ImageDto(request.getAd().getAdData().getMainImage());
        //}
        //if (request.getAd().getAdData().getThumbImage()!= null) {
        //    this.imageThumbnail = new ImageDto(request.getAd().getAdData().getThumbImage());
        //}
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Date getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Date requestedAt) {
        this.requestedAt = requestedAt;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    //public ImageDto getImage() {
    //    return image;
    //}

    //public void setImage(ImageDto image) {
    //    this.image = image;
    //}

    //public ImageDto getImageThumbnail() {
    //    return imageThumbnail;
    //}

    //public void setImageThumbnail(ImageDto imageThumbnail) {
    //    this.imageThumbnail = imageThumbnail;
    //}

    public AdType getType() {
        return type;
    }

    public void setType(AdType type) {
        this.type = type;
    }

    public AdStatus getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(AdStatus adStatus) {
        this.adStatus = adStatus;
    }

    public Date getAdExpiresAt() {
        return adExpiresAt;
    }

    public void setAdExpiresAt(Date adExpiresAt) {
        this.adExpiresAt = adExpiresAt;
    }
    
    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
    
    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Integer getNumUnreadMessages() {
        return numUnreadMessages;
    }

    public void setNumUnreadMessages(Integer numUnreadMessages) {
        this.numUnreadMessages = numUnreadMessages;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }
}
