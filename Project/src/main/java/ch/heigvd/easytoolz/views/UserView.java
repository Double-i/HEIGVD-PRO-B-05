package ch.heigvd.easytoolz.views;

import ch.heigvd.easytoolz.models.Address;

public interface UserView
{
    String getUserName();
    String getEmail();

    AddressView getAddress();
}