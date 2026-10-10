package entities;

import entities.wallet.StoreWallet;

public class Store {
    private int storeId;
    private String storeName;
    private String storeType;
    private String location;
    private StoreWallet storeWallet;

    public Store(int storeId,
        String storeName,
        String storeType,
        String location,
        StoreWallet storeWallet
    ){
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeType = storeType;
        this.location = location;
        this.storeWallet = storeWallet;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreType() {
        return storeType;
    }

    public String getLocation() {
        return location;
    }

    public StoreWallet getStoreWallet() {
        return storeWallet;
    }
}
