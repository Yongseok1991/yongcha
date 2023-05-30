package yong.app.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Address {

    @Column(name = "address_city")
    private String city;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_zipcode")
    private String zipcode;

    @Column(name = "address_detail")
    private String detail;

    protected Address() {}

    public Address(String city, String street, String zipcode, String detail) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.detail = detail;
    }
}