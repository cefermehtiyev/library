package az.ingress.controller;

import az.ingress.model.request.CommonStatusRequest;
import az.ingress.service.abstraction.CommonStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/statuses")
@RequiredArgsConstructor
public class StatusController {
    private final CommonStatusService commonStatusService;

    @PostMapping
    public void addStatus(@RequestBody CommonStatusRequest commonStatusRequest){
        commonStatusService.addStatus(commonStatusRequest);
    }
}
