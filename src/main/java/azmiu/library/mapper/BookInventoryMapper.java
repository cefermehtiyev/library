package azmiu.library.mapper;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.CategoryEntity;
import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.dao.entity.FileEntity;
import azmiu.library.dao.entity.ImageEntity;
import azmiu.library.dao.entity.InventoryStatusEntity;
import azmiu.library.model.response.BookInventoryResponse;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import org.springframework.data.domain.Page;

public enum BookInventoryMapper {
    BOOK_INVENTORY_MAPPER;

    public BookInventoryEntity buildBookInventoryEntity(FileEntity file, ImageEntity image, String title, Integer publicationYear, InventoryStatusEntity inventoryStatus, CommonStatusEntity commonStatus) {
        return BookInventoryEntity.builder()
                .title(title)
                .publicationYear(publicationYear)
                .file(file)
                .image(image)
                .availableQuantity(1)
                .borrowedQuantity(0)
                .reservedQuantity(1)
                .inventoryStatus(inventoryStatus)
                .commonStatus(commonStatus)
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

    private BookResponse buildBookResponse(BookInventoryEntity bookInventoryEntity){
        var bookEntity = bookInventoryEntity.getBooks().getFirst();
        return BookMapper.BOOK_MAPPER.buildBookResponse(bookEntity);

    }

    public PageableResponse<BookResponse> pageableBookInventoryResponse(Page<BookInventoryEntity> inventoryEntityPage) {
        return PageableResponse.<BookResponse>builder()
                .list((inventoryEntityPage.map(this::buildBookResponse).toList()))
                .currentPageNumber(inventoryEntityPage.getNumber())
                .totalPages(inventoryEntityPage.getTotalPages())
                .totalElements(inventoryEntityPage.getTotalElements())
                .numberOfElements(inventoryEntityPage.getNumberOfElements())
                .hasNextPage(inventoryEntityPage.getNumber() < (inventoryEntityPage.getTotalPages() - 1))
                .build();
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
