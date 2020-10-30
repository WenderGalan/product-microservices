package io.github.wendergalan.pagamento.controllers;

import io.github.wendergalan.pagamento.data.vo.VendaVO;
import io.github.wendergalan.pagamento.services.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {
    private final VendaService vendaService;
    private final PagedResourcesAssembler<VendaVO> assembler;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public VendaVO findById(@PathVariable("id") Long id) {
        VendaVO vendaVO = vendaService.findById(id);
        vendaVO.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel());
        return vendaVO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "limit", defaultValue = "12") int limit,
                                  @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        var sortDirection = "DESC".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "data"));
        Page<VendaVO> vendas = vendaService.findAll(pageable);
        vendas.stream().forEach(p -> p.add(linkTo(methodOn(VendaController.class).findById(p.getId())).withSelfRel()));
        PagedModel<EntityModel<VendaVO>> pagedModel = assembler.toModel(vendas);
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public VendaVO create(@RequestBody VendaVO vendaVO) {
        VendaVO prodVO = vendaService.create(vendaVO);
        prodVO.add(linkTo(methodOn(VendaController.class).findById(prodVO.getId())).withSelfRel());
        return prodVO;
    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public VendaVO update(@RequestBody VendaVO vendaVO) {
        VendaVO prodVO = vendaService.update(vendaVO);
        prodVO.add(linkTo(methodOn(VendaController.class).findById(prodVO.getId())).withSelfRel());
        return prodVO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        vendaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
