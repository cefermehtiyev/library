package azmiu.library.service.concurate;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.SavedBookEntity;
import azmiu.library.dao.repository.SavedBookRepository;
import azmiu.library.exception.AlreadyExistsException;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.mapper.SavedBookMapper;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.SavedBookResponse;
import azmiu.library.service.abstraction.BookInventoryService;
import azmiu.library.service.abstraction.SavedBookService;
import azmiu.library.service.abstraction.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static azmiu.library.exception.ErrorMessage.SAVED_BOOK_NOT_FOUND;
import static azmiu.library.mapper.SavedBookMapper.SAVED_BOOK_MAPPER;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class SavedBookServiceHandler implements SavedBookService {
    SavedBookRepository savedBookRepository;
    UserService userService;
    BookInventoryService bookInventoryService;

    @Override
    public void saveBook(Long userId, Long bookInventoryId) {
        if(savedBookRepository.existsByUserIdAndBookInventoryId(userId, bookInventoryId)){
            throw new AlreadyExistsException(ErrorMessage.SAVED_BOOK_ALREADY_EXISTS.getMessage());
        }
        var user = userService.getUserEntity(userId);
        var bookInventory = bookInventoryService.getBookInventoryEntity(bookInventoryId);
        savedBookRepository.save(SAVED_BOOK_MAPPER.buildSavedBooKEntity(user,bookInventory));
    }



//    @Override
//    public List<SavedBookResponse> getUserSavedBooks(Long userId) {
//        var user = userService.getUserEntity(userId);
//
//        return user.getSavedBooks().stream()
//                .map(SAVED_BOOK_MAPPER::buildSavedBookResponse).toList();
//    }

    public PageableResponse<SavedBookResponse> getUserSavedBooks(Long userId, PageCriteria pageCriteria){
        var page = savedBookRepository.findAllByUserId(userId,
                PageRequest.of(pageCriteria.getPage(),pageCriteria.getCount()));

        return SAVED_BOOK_MAPPER.buildPageableResponse(page);
    }

    @Override
    public void deleteBook(Long id) {
        var savedBookEntity = findById(id);
        savedBookRepository.delete(savedBookEntity);
    }

    private SavedBookEntity findById(Long id){
        return savedBookRepository.findById(id).orElseThrow(
                () -> new NotFoundException(SAVED_BOOK_NOT_FOUND.getMessage())
        );

    }

}
