package ch.heigvd.easytoolz.views;

import java.math.BigDecimal;

public interface AddressView {
    String getAddress();
    String getPostalCode();
    BigDecimal getLat();
    BigDecimal getLng();
}
