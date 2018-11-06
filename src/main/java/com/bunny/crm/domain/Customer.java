package com.bunny.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.bunny.crm.domain.enumeration.Level;

import com.bunny.crm.domain.enumeration.IntentionProduct;

import com.bunny.crm.domain.enumeration.Source;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 姓名
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "姓名", required = true)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    /**
     * 客户等级 A/B/C
     */
    @ApiModelProperty(value = "客户等级 A/B/C")
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_level")
    private Level level;

    /**
     * 联系电话
     */
    @NotNull
    @Size(min = 11, max = 11)
    @ApiModelProperty(value = "联系电话", required = true)
    @Column(name = "phone", length = 11, nullable = false)
    private String phone;

    /**
     * 意向产品 类型:1 住房,2 公寓,3 商业
     */
    @ApiModelProperty(value = "意向产品 类型:1 住房,2 公寓,3 商业")
    @Enumerated(EnumType.STRING)
    @Column(name = "intention_product")
    private IntentionProduct intentionProduct;

    /**
     * 抗性，阻力
     */
    @Size(max = 500)
    @ApiModelProperty(value = "抗性，阻力")
    @Column(name = "resistance", length = 500)
    private String resistance;

    /**
     * 意向价格
     */
    @Size(max = 100)
    @ApiModelProperty(value = "意向价格")
    @Column(name = "intention_price", length = 100)
    private String intentionPrice;

    /**
     * 意向面积
     */
    @Size(max = 100)
    @ApiModelProperty(value = "意向面积")
    @Column(name = "intention_space", length = 100)
    private String intentionSpace;

    /**
     * 客户来源:1 电邀,2 传单,3 ?
     */
    @ApiModelProperty(value = "客户来源:1 电邀,2 传单,3 ?")
    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private Source source;

    /**
     * 需求区域
     */
    @ApiModelProperty(value = "需求区域")
    @Column(name = "demand_area")
    private String demandArea;

    /**
     * 到访次数
     */
    @ApiModelProperty(value = "到访次数")
    @Column(name = "visit_number")
    private Integer visitNumber;

    /**
     * 居住地址
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "居住地址")
    @Column(name = "home_address", length = 1000)
    private String homeAddress;

    /**
     * 客户职业
     */
    @Size(max = 20)
    @ApiModelProperty(value = "客户职业")
    @Column(name = "profession", length = 20)
    private String profession;

    /**
     * 备注
     */
    @Size(max = 2000)
    @ApiModelProperty(value = "备注")
    @Column(name = "remark", length = 2000)
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private ZonedDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    @Column(name = "del_flag")
    private Boolean delFlag;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Level getLevel() {
        return level;
    }

    public Customer level(Level level) {
        this.level = level;
        return this;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public Customer phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public IntentionProduct getIntentionProduct() {
        return intentionProduct;
    }

    public Customer intentionProduct(IntentionProduct intentionProduct) {
        this.intentionProduct = intentionProduct;
        return this;
    }

    public void setIntentionProduct(IntentionProduct intentionProduct) {
        this.intentionProduct = intentionProduct;
    }

    public String getResistance() {
        return resistance;
    }

    public Customer resistance(String resistance) {
        this.resistance = resistance;
        return this;
    }

    public void setResistance(String resistance) {
        this.resistance = resistance;
    }

    public String getIntentionPrice() {
        return intentionPrice;
    }

    public Customer intentionPrice(String intentionPrice) {
        this.intentionPrice = intentionPrice;
        return this;
    }

    public void setIntentionPrice(String intentionPrice) {
        this.intentionPrice = intentionPrice;
    }

    public String getIntentionSpace() {
        return intentionSpace;
    }

    public Customer intentionSpace(String intentionSpace) {
        this.intentionSpace = intentionSpace;
        return this;
    }

    public void setIntentionSpace(String intentionSpace) {
        this.intentionSpace = intentionSpace;
    }

    public Source getSource() {
        return source;
    }

    public Customer source(Source source) {
        this.source = source;
        return this;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getDemandArea() {
        return demandArea;
    }

    public Customer demandArea(String demandArea) {
        this.demandArea = demandArea;
        return this;
    }

    public void setDemandArea(String demandArea) {
        this.demandArea = demandArea;
    }

    public Integer getVisitNumber() {
        return visitNumber;
    }

    public Customer visitNumber(Integer visitNumber) {
        this.visitNumber = visitNumber;
        return this;
    }

    public void setVisitNumber(Integer visitNumber) {
        this.visitNumber = visitNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public Customer homeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
        return this;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getProfession() {
        return profession;
    }

    public Customer profession(String profession) {
        this.profession = profession;
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRemark() {
        return remark;
    }

    public Customer remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Customer createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Customer updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean isDelFlag() {
        return delFlag;
    }

    public Customer delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public User getUser() {
        return user;
    }

    public Customer user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        if (customer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customer{" +
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
            "}";
    }
}
