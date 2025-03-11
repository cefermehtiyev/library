package azmiu.library.mapper;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.dao.entity.InventoryStatusEntity;

public enum BookInventoryMapper {
    BOOK_INVENTORY_MAPPER;

    public BookInventoryEntity buildBookInventoryEntity(String title, Integer publicationYear, InventoryStatusEntity inventoryStatus) {

        return BookInventoryEntity.builder()
                .title(title)
                .publicationYear(publicationYear)
                .availableQuantity(1)
                .borrowedQuantity(0)
                .reservedQuantity(1)
                .inventoryStatus(inventoryStatus)
                .readCount(0L)
                .build();
    }


    public BookInventoryEntity updateBookInventoryEntity(BookInventoryEntity bookInventoryEntity) {
        bookInventoryEntity.setReservedQuantity(bookInventoryEntity.getReservedQuantity() + 1);
        bookInventoryEntity.setAvailableQuantity(bookInventoryEntity.getAvailableQuantity() + 1);


        return bookInventoryEntity;

    }

    public void decreaseAvailableIncreaseBorrowed(BookInventoryEntity bookInventoryEntity) {
        bookInventoryEntity.setAvailableQuantity(bookInventoryEntity.getAvailableQuantity() - 1);
        bookInventoryEntity.setBorrowedQuantity(bookInventoryEntity.getBorrowedQuantity() + 1);


    }

    public void updateInventoryOnReturn(BookInventoryEntity bookInventoryEntity) {
        bookInventoryEntity.setBorrowedQuantity(bookInventoryEntity.getBorrowedQuantity() - 1);
        bookInventoryEntity.setAvailableQuantity(bookInventoryEntity.getAvailableQuantity() + 1);


    }

    public void increaseReadCount(BookInventoryEntity bookInventoryEntity) {
        bookInventoryEntity.setReadCount(bookInventoryEntity.getReadCount() + 1);
    }


}
