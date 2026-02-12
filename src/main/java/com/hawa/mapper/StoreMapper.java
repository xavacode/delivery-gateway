package com.hawa.mapper;

import com.hawa.dto.store.StoreCreateRequestDto;
import com.hawa.model.Store;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoreMapper {

    Store storeCreateRequestDtoToStore(StoreCreateRequestDto storeCreateRequestDto);

}
