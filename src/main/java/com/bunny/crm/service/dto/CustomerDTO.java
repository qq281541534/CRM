package com.bunny.crm.service.dto;

import com.bunny.crm.domain.enumeration.IntentionProduct;
import com.bunny.crm.domain.enumeration.Level;
import com.bunny.crm.domain.enumeration.Source;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the Customer entity.
 */
public class CustomerDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String name;

    private Level level;

    @NotNull
    @Size(min = 11, max = 11)
    private String phone;

    private IntentionProduct intentionProduct;

    @Size(max = 500)
    private String resistance;

    @Size(max = 100)
    private String intentionPrice;

    @Size(max = 100)
    private String intentionSpace;

    private Source source;

    private String demandArea;

    private Integer visitNumber;

    @Size(max = 1000)
    private String homeAddress;

    @Size(max = 20)
    private String profession;

    @Size(max = 2000)
    private String remark;

    private ZonedDateTime createTime;

    private ZonedDateTime updateTime;

    private Boolean delFlag;

    private Long userId;

    private String firstName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public IntentionProduct getIntentionProduct() {
        return intentionProduct;
    }

    public void setIntentionProduct(IntentionProduct intentionProduct) {
        this.intentionProduct = intentionProduct;
    }

    public String getResistance() {
        return resistance;
    }

    public void setResistance(String resistance) {
        this.resistance = resistance;
    }

    public String getIntentionPrice() {
        return intentionPrice;
    }

    public void setIntentionPrice(String intentionPrice) {
        this.intentionPrice = intentionPrice;
    }

    public String getIntentionSpace() {
        return intentionSpace;
    }

    public void setIntentionSpace(String intentionSpace) {
        this.intentionSpace = intentionSpace;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getDemandArea() {
        return demandArea;
    }

    public void setDemandArea(String demandArea) {
        this.demandArea = demandArea;
    }

    public Integer getVisitNumber() {
        return visitNumber;
    }

    public void setVisitNumber(Integer visitNumber) {
        this.visitNumber = visitNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;
        if (customerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", level='" + getLevel() + "'" +
            ", phone='" + getPhone() + "'" +
            ", intentionProduct='" + getIntentionProduct() + "'" +
            ", resistance='" + getResistance() + "'" +
            ", intentionPrice='" + getIntentionPrice() + "'" +
            ", intentionSpace='" + getIntentionSpace() + "'" +
            ", source='" + getSource() + "'" +
            ", demandArea='" + getDemandArea() + "'" +
            ", visitNumber=" + getVisitNumber() +
            ", homeAddress='" + getHomeAddress() + "'" +
            ", profession='" + getProfession() + "'" +
            ", remark='" + getRemark() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", delFlag='" + isDelFlag() + "'" +
            ", user=" + getUserId() +
            "}";
    }
}
