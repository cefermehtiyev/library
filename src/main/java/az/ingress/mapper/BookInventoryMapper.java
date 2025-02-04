package az.ingress.mapper;

import az.ingress.dao.entity.BookInventoryEntity;
import az.ingress.model.enums.InventoryStatus;

import static az.ingress.model.enums.InventoryStatus.IN_STOCK;
import static az.ingress.model.enums.InventoryStatus.LOW_STOCK;
import static az.ingress.model.enums.InventoryStatus.OUT_OF_STOCK;

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
                .readCount(0L)
                .status(LOW_STOCK)
                .build();
    }


    public BookInventoryEntity updateBookInventoryEntity(BookInventoryEntity bookInventoryEntity){
        bookInventoryEntity.setReservedQuantity(bookInventoryEntity.getReservedQuantity()+ 1 );
        bookInventoryEntity.setAvailableQuantity(bookInventoryEntity.getAvailableQuantity() + 1);

        determineInventoryStatus(bookInventoryEntity);

        return bookInventoryEntity;

    }
    
    public void decreaseAvailableIncreaseBorrowed(BookInventoryEntity bookInventoryEntity){
        bookInventoryEntity.setAvailableQuantity(bookInventoryEntity.getAvailableQuantity() - 1);
        bookInventoryEntity.setBorrowedQuantity(bookInventoryEntity.getBorrowedQuantity() + 1);

        determineInventoryStatus(bookInventoryEntity);

    }

    public void updateInventoryOnReturn (BookInventoryEntity bookInventoryEntity){
        bookInventoryEntity.setBorrowedQuantity(bookInventoryEntity.getBorrowedQuantity() - 1);
        bookInventoryEntity.setAvailableQuantity(bookInventoryEntity.getAvailableQuantity() + 1);

        determineInventoryStatus(bookInventoryEntity);

    }

    public void determineInventoryStatus (BookInventoryEntity bookInventoryEntity){
        if (bookInventoryEntity.getAvailableQuantity() == 0){
            bookInventoryEntity.setStatus(OUT_OF_STOCK);
        } else if (bookInventoryEntity.getReservedQuantity() <= 2) {
            bookInventoryEntity.setStatus(LOW_STOCK);
        }else {
            bookInventoryEntity.setStatus(IN_STOCK);
        }
    }

    public void increaseReadCount(BookInventoryEntity bookInventoryEntity){
        bookInventoryEntity.setReadCount(bookInventoryEntity.getReadCount() + 1);
    }


}
