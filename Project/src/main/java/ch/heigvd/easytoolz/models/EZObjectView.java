package ch.heigvd.easytoolz.models;

public interface EZObjectView {
    String getName();
    String getDescription();
    String getOwner();

    AddressView getAddress();
}
