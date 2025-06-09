package azmiu.library.controller;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.criteria.UserCriteria;
import azmiu.library.model.response.AdminResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.UserResponse;
import azmiu.library.service.abstraction.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/all")
//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public PageableResponse<AdminResponse> getAllAdmins(PageCriteria pageCriteria, UserCriteria userCriteria) {
        return adminService.getAllAdmins(pageCriteria, userCriteria);
    }
}
