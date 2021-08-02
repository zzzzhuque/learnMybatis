package com.zt.repository;

import com.zt.entity.Customer;

public interface CustomerRepository {
    public Customer findById(long id);
}
