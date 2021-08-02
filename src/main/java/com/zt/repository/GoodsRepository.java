package com.zt.repository;

import com.zt.entity.Goods;

public interface GoodsRepository {
    public Goods findById(long id);
}
