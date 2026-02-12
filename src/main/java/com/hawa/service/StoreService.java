package com.hawa.service;

import com.hawa.model.Store;

public interface StoreService {

    Store saveStore(Store store);
    Store getStore(long storeId);
}
