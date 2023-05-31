package yong.app.domain.base;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String city;

    private String street;
    private String zipCode;
    private String detail;

    protected Address() {}

    public Address(String city, String street, String zipCode, String detail) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.detail = detail;
    }
}
