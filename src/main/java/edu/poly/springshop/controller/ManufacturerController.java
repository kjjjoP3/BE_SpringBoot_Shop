package edu.poly.springshop.controller;

import edu.poly.springshop.domain.Manufacturer;
import edu.poly.springshop.dto.ManufacturerDto;
import edu.poly.springshop.exception.ApiResponse;
import edu.poly.springshop.exception.FileNotFoundException;
import edu.poly.springshop.exception.FileStorageException;
import edu.poly.springshop.exception.ManufacturerException;
import edu.poly.springshop.repository.ManufacturerRepository;
import edu.poly.springshop.service.FileStorageService;
import edu.poly.springshop.service.ManufacturerService;
import edu.poly.springshop.service.MapValidationErrorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/manufacturer")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;
    @Autowired
    private ManufacturerRepository manufacturerRepository;


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
    MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    MediaType.MULTIPART_FORM_DATA_VALUE},
    produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createManafacturer(@Valid @ModelAttribute ManufacturerDto dto,
                                                BindingResult result
                                                ){
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);

        if(responseEntity != null){
            return responseEntity;
        }
        Manufacturer entity = manufacturerService.insertManufacturer(dto);
        dto.setId(entity.getId());
        dto.setLogo(entity.getLogo());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    @PatchMapping(value = "/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateManufacturer(@PathVariable Long id,
                                                @Valid @ModelAttribute ManufacturerDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Validation error"));
        }

        try {
            Manufacturer manufacturer = manufacturerService.updateManufacturer(id, dto);

            dto.setId(manufacturer.getId());
            dto.setName(manufacturer.getName());
            dto.setLogo(manufacturer.getLogo());

            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (ManufacturerException ex) {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/logo/{filename:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename, HttpServletRequest request){
        Resource resource = fileStorageService.loadLogoFileAsResource(filename);

        String contentType = null;

        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }
        catch (Exception ex){

            throw new FileStorageException("Could not determine file type.");

        }

        if(contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""
                +resource.getFilename() + "\"")
                .body(resource);
    }



    @GetMapping
    public ResponseEntity<?> getManufacturers(){

        var list = manufacturerService.findAll();
        var newlist = list.stream().map(item->{
            ManufacturerDto dto = new ManufacturerDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(newlist, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<?> getManufacturers(@RequestParam("query") String query,
                                              @PageableDefault(size = 2, sort = "name", direction = Sort.Direction.ASC)Pageable pageable
                                              ){

        var list = manufacturerService.findByName(query,pageable);

        var newlist = list.getContent().stream().map(item->{
            ManufacturerDto dto = new ManufacturerDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());

        var newPage = new PageImpl<ManufacturerDto>(newlist, list.getPageable(), list.getTotalPages());

        return new ResponseEntity<>(newPage, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> getManufacturers(@PageableDefault(size = 5,sort = "name", direction = Sort.Direction.ASC)
                                              Pageable pageable
                                              ){

        var list = manufacturerService.findAll(pageable);
        var newlist = list.stream().map(item->{
            ManufacturerDto dto = new ManufacturerDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(newlist, HttpStatus.OK);
    }


    @GetMapping("/{id}/get")
    public ResponseEntity<?> getManufacturers(@PathVariable Long id){

            var entity = manufacturerService.findById(id);

            ManufacturerDto dto = new ManufacturerDto();

            BeanUtils.copyProperties(entity, dto);


        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManufacturers(@PathVariable Long id){
        manufacturerService.deleteById(id);

        return new ResponseEntity<>("Category with id" + id + "was deleted", HttpStatus.OK);
    }


}
