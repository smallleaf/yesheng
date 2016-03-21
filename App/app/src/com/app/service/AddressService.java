package com.app.service;

import java.util.List;

import com.app.entity.Address;

public interface AddressService {
	public Address addAddress(Address addr);
	public Address updateAddress(Address addr);
	public int deleteAddress(int id);
	public List<Address> queryAddress(String uid);
}
