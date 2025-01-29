package az.ingress.mapper;

import az.ingress.dao.entity.BookInventoryEntity;
import az.ingress.model.enums.InventoryStatus;

public enum BookInventoryMapper {
    BOOK_INVENTORY_MAPPER;

    public BookInventoryEntity buildBookInventoryEntity(String title , Integer publicationYear){

        return BookInventoryEntity
                .builder()
                .title(title)
                .publicationYear(publicationYear)
                .availableQuantity(1)
                .borrowedQuantity(0)
                .reservedQuantity(1)
                .status(InventoryStatus.LOW_STOCK)
                .build();
    }


    public BookInventoryEntity updateBookInventoryEntity(BookInventoryEntity bookInventoryEntity){
        bookInventoryEntity.setReservedQuantity(bookInventoryEntity.getReservedQuantity()+ 1 );
        bookInventoryEntity.setAvailableQuantity(bookInventoryEntity.getAvailableQuantity() + 1);

        if(bookInventoryEntity.getReservedQuantity() > 2){
            bookInventoryEntity.setStatus(InventoryStatus.IN_STOCK);
        }

        return bookInventoryEntity;

    }
    
    public void decreaseAvailableIncreaseBorrowed(BookInventoryEntity bookInventoryEntity){
        bookInventoryEntity.setAvailableQuantity(bookInventoryEntity.getAvailableQuantity() - 1);
        bookInventoryEntity.setBorrowedQuantity(bookInventoryEntity.getBorrowedQuantity() + 1);

        if (bookInventoryEntity.getAvailableQuantity() < 3) {
            bookInventoryEntity.setStatus(InventoryStatus.LOW_STOCK);
        }
    }
}
