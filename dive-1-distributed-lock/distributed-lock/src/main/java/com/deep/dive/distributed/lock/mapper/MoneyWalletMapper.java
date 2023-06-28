package com.deep.dive.distributed.lock.mapper;


import com.deep.dive.distributed.lock.model.MoneyWalletDTO;

public class MoneyWalletMapper {

    private MoneyWalletMapper() {
    }


    public static MoneyWalletDTO cacheObjToMoneyWalletDTO(Object cachedValue) {
        return (MoneyWalletDTO) cachedValue;
    }
}
