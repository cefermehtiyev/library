package azmiu.library.controller


import azmiu.library.criteria.BookCriteria
import azmiu.library.criteria.PageCriteria
import azmiu.library.model.request.BookRequest
import azmiu.library.service.abstraction.BookService
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

class BookControllerTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private BookService bookService
    private MockMvc mockMvc

    def setup() {
        bookService = Mock()
        def bookController = new BookController(bookService)
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice()
                .build()
    }


    def "TestGetBook"() {
        given:
        def id = random.nextLong()
        def url = "/v1/books/$id"

        when:
        def result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * bookService.getBook(id)
    }


    def "TestGetAllBooks"() {
        given:
        def pageCriteria = random.nextObject(PageCriteria)
        def bookCriteria = random.nextObject(BookCriteria)
        def url = "/v1/books/sorted"

        when:
        def result = mockMvc
                .perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", pageCriteria.page.toString())
                        .param("count", pageCriteria.count.toString())
                        .param("title", bookCriteria.title)
                        .param("author", bookCriteria.author)
                        .param("language", bookCriteria.language)
                        .param("publicationYearFrom", bookCriteria.publicationYearFrom?.toString())
                        .param("publicationYearTo", bookCriteria.publicationYearTo?.toString())
                        .param("readCount", bookCriteria.readCount?.toString()))
                .andReturn()

        then:
        1 * bookService.getAllBooks(pageCriteria, bookCriteria)
    }

    def "TestGetBookSorted"() {
        given:
        def order = "asc"
        def pageCriteria = random.nextObject(PageCriteria)
        def url = "/v1/books/sorted/page"

        when:
        def result = mockMvc
                .perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("order", order)
                        .param("page", pageCriteria.page.toString())
                        .param("count", pageCriteria.count.toString())
                )
                .andReturn()
        then:
        1 * bookService.getBooksSorted(order, pageCriteria)
    }

    def "TestUpdateBook"() {
        given:
        def id = random.nextLong()
        def bookRequest = random.nextObject(BookRequest)
        def requestBody = """
                    {
                        "categoryId": "$bookRequest.categoryId",
                        "title": "$bookRequest.title",
                        "author": "$bookRequest.author",
                        "bookCode": "$bookRequest.bookCode",
                        "publisher": "$bookRequest.publisher",
                        "language": "$bookRequest.language",
                        "description": "$bookRequest.description",
                        "pages": $bookRequest.pages,
                        "publicationYear": $bookRequest.publicationYear
                    }
                """
        def url = "/v1/books/$id"
        when:
        def result = mockMvc
                .perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andReturn()

        then:
        1 * bookService.updateAllInstancesForBook(id,bookRequest)
        def response = result.response
        response .status == HttpStatus.NO_CONTENT.value()
    }


}

