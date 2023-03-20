package by.it_academy.audit.web.controllers;

import by.it_academy.audit.core.dto.AuditDTO;
import by.it_academy.audit.core.dto.OnePage;
import by.it_academy.audit.service.api.IAuditService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/audit")
public class AuditController {
    private final IAuditService auditService;

    public AuditController(IAuditService auditService) {
        this.auditService = auditService;
    }
    @RequestMapping(path = "/inner", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid AuditDTO auditDTO){
        auditService.save(auditDTO);
    }
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public OnePage<AuditDTO>getPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                    @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return auditService.getPage(pageable);
    }
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AuditDTO get(@PathVariable("uuid") UUID uuid){
        return auditService.get(uuid);
    }
}
