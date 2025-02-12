package az.ingress.controller

import az.ingress.criteria.BookCriteria
import az.ingress.criteria.PageCriteria
import az.ingress.service.abstraction.BookService
import az.ingress.service.abstraction.FileService
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

class BookControllerTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private BookService bookService
    private FileService fileService
    private MockMvc mockMvc

    def setup() {
        bookService = Mock()
        fileService = Mock()
        def bookController = new BookController(bookService, fileService)
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice()
                .build()
    }

    def "TestBorrowBook"() {
        given:
        def fin = random.nextObject(String)
        def bookCode = random.nextObject(String)
        def url = "/v1/books/borrow"

        when:
        def result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .param("fin", fin)
                .param("bookCode", bookCode))
                .andReturn()

        then:
        1 * bookService.borrowBook(fin, bookCode)
        def response = result.response
        response.status == HttpStatus.NO_CONTENT.value()

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

    def "TestUploadBook"() {
        given:
        def id = random.nextLong()
        def url = "/v1/books/upload"

        when:
        def result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", id.toString()))
                .andReturn()
        then:
        1 * fileService.downloadFile(id)

    }

    def "TestProcessBookReturn"() {
        given:
        def fin = random.nextObject(String)
        def bookCode = random.nextObject(String)
        def url = "/v1/books/return"

        when:
        def result = mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fin", fin)
                        .param("bookCode", bookCode))
                .andReturn()

        then:
        1 * bookService.processBookReturn(fin, bookCode)
    }

    def "TestUpdateBookCategory"() {
        given:
        def bookId = random.nextLong()
        def categoryId = random.nextLong()

        def url = "/v1/books/update-category"
        when:
        def result = mockMvc
                .perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("bookId", bookId.toString())
                        .param("categoryId", categoryId.toString()))
                .andReturn()


        then:
        1 * bookService.updateBookCategory(bookId, categoryId)

    }

    def "TestGEtAllBooksByFin"() {
        given:
        def fin = random.nextObject(String)
        def url = "/v1/books/by-fin"

        when:
        def result = mockMvc
                .perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fin", fin))
                .andReturn()

        then:
        1 * bookService.getAllBooksByFin(fin)
    }

    def "TestGetAllBooks"() {
        given:
        def bookCriteria = random.nextObject(BookCriteria)
        def pageCriteria = random.nextObject(PageCriteria)
        def url = "/v1/books"

        when:
        def result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", pageCriteria.page.toString())
                .param("count", pageCriteria.count.toString())
                .param("title", bookCriteria.title)
                .param("author", bookCriteria.author)
                .param("language", bookCriteria.language)
                .param("publicationYearFrom", bookCriteria.publicationYearFrom.toString())
                .param("publicationYearTo", bookCriteria.publicationYearTo.toString())
                .param("readCount", bookCriteria.readCount.toString()))
                .andReturn()

        then:
        1 * bookService.getAllBooks(pageCriteria, bookCriteria)
    }

    def "TestGetBooksSortedByReadCount"() {
        given:
        def pageCriteria = random.nextObject(PageCriteria)
        def url = "/v1/books/sorted-by-read-count"

        when:
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", pageCriteria.page.toString())
                .param("count", pageCriteria.count.toString()))
                .andReturn()

        then:
        1 * bookService.getBooksSortedByReadCount(pageCriteria)
    }

    def "TestGetBooksSortedByPagesDesc"() {
        given:
        def pageCriteria = random.nextObject(PageCriteria)
        def url = "/v1/books/sorted-by-pages-desc"

        when:
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", pageCriteria.page.toString())
                .param("count", pageCriteria.count.toString()))
                .andReturn()

        then:
        1 * bookService.getBooksSorted(pageCriteria)
    }
    def "TestGetBooksSortedByPagesAsc"() {
        given:
        def pageCriteria = random.nextObject(PageCriteria)
        def url = "/v1/books/sorted-by-pages-asc"

        when:
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", pageCriteria.page.toString())
                .param("count", pageCriteria.count.toString()))
                .andReturn()

        then:
        1 * bookService.getBooksSortedByPagesAsc(pageCriteria)
    }




}
