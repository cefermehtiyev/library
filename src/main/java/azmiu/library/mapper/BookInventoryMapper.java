package azmiu.library.mapper;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.CategoryEntity;
import azmiu.library.dao.entity.InventoryStatusEntity;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.BookInventoryResponse;

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

    public BookInventoryResponse buildBookInventoryResponse(BookInventoryEntity bookInventoryEntity){
        return BookInventoryResponse.builder()
                .title(bookInventoryEntity.getTitle())
                .reservedQuantity(bookInventoryEntity.getReservedQuantity())
                .availableQuantity(bookInventoryEntity.getAvailableQuantity())
                .borrowedQuantity(bookInventoryEntity.getBorrowedQuantity())
                .publicationYear(bookInventoryEntity.getPublicationYear())
                .inventoryStatus(bookInventoryEntity.getInventoryStatus().getStatus())
                .readCount(bookInventoryEntity.getReadCount())
                .build();
    }

    public void updateBookInventory(BookInventoryEntity bookInventoryEntity, CategoryEntity categoryEntity, String title, Integer publicationYear){
         bookInventoryEntity.setTitle(title);
         bookInventoryEntity.setPublicationYear(publicationYear);
         bookInventoryEntity.setCategory(categoryEntity);
    }

    public BookInventoryEntity increaseBookInventoryQuantities(BookInventoryEntity bookInventoryEntity) {
        bookInventoryEntity.setReservedQuantity(bookInventoryEntity.getReservedQuantity() + 1);
        bookInventoryEntity.setAvailableQuantity(bookInventoryEntity.getAvailableQuantity() + 1);
        return bookInventoryEntity;
    }

    public void decreaseAvailableIncreaseBorrowed(BookInventoryEntity bookInventoryEntity) {
        bookInventoryEntity.setAvailableQuantity(bookInventoryEntity.getAvailableQuantity() - 1);
        bookInventoryEntity.setBorrowedQuantity(bookInventoryEntity.getBorrowedQuantity() + 1);


    }

    public void updateCountsOnBookDeleted(BookInventoryEntity bookInventoryEntity){
        bookInventoryEntity.setAvailableQuantity(bookInventoryEntity.getAvailableQuantity() - 1);
        bookInventoryEntity.setReservedQuantity(bookInventoryEntity.getReservedQuantity()  - 1);
    }

    public void updateInventoryOnReturn(BookInventoryEntity bookInventoryEntity) {
        bookInventoryEntity.setBorrowedQuantity(bookInventoryEntity.getBorrowedQuantity() - 1);
        bookInventoryEntity.setAvailableQuantity(bookInventoryEntity.getAvailableQuantity() + 1);


    }

    public void increaseReadCount(BookInventoryEntity bookInventoryEntity) {
        bookInventoryEntity.setReadCount(bookInventoryEntity.getReadCount() + 1);
    }


}
