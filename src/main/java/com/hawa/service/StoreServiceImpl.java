package com.hawa.service;

import com.hawa.constant.ErrorCodeConstant;
import com.hawa.exception.NotFoundException;
import com.hawa.model.Store;
import com.hawa.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ApiKeyService apiKeyService;

    @Override
    @Transactional
    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    @Transactional(readOnly = true)
    public Store getStore(long storeId) {
        return storeRepository.findById(storeId).orElseThrow(
                () -> new NotFoundException(String.format(ErrorCodeConstant.NOT_FOUND.getMessage(), "StoreId"))
        );
    }
}
