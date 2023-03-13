package edu.poly.springshop.controller;


import edu.poly.springshop.dto.ManufacturerDto;
import edu.poly.springshop.dto.ProductDto;
import edu.poly.springshop.dto.ProductImageDto;
import edu.poly.springshop.exception.FileStorageException;
import edu.poly.springshop.service.FileStorageService;
import edu.poly.springshop.service.MapValidationErrorService;
import edu.poly.springshop.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @Autowired
    ProductService productService;

    @PostMapping()
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto dto, BindingResult result){
            ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);
            if(responseEntity != null){
                return responseEntity;
            }

            var saveDto = productService.insertProduct(dto);

            return new ResponseEntity<>(saveDto, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}/all")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @Valid @RequestBody ProductDto dto,
                                           BindingResult result){

        System.out.println("Update Product");
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);
        if(responseEntity != null){
            return responseEntity;
        }

        var updateDto = productService.updateProduct(id,dto);
        return new ResponseEntity<>(updateDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);

        return new ResponseEntity<>("Product with id: " +id+ "was deleted", HttpStatus.OK );
    }

    @PostMapping(value = "/images/one",
        consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
                MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                MediaType.APPLICATION_JSON_VALUE
        },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile imageFile){

        var fileInfo = fileStorageService.storeUploadedProductImageFile(imageFile);
        ProductImageDto dto = new ProductImageDto();
        BeanUtils.copyProperties(fileInfo, dto);

        dto.setStatus("done");

        dto.setUrl("http://localhost:8080/api/v1/products/images/"+fileInfo.getFileName());



        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename, HttpServletRequest request){
        Resource resource = fileStorageService.loadProductImageFileAsResource(filename);

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

    @GetMapping("/find")
    public ResponseEntity<?> getProductBriefsByName(@RequestParam("query") String query,
                                              @PageableDefault(size = 5, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return new ResponseEntity<>(productService.getProductBriefsByName(query, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}/getedit")
    public ResponseEntity<?> getEditedProduct(@PathVariable Long id){
        return new ResponseEntity<>(productService.getEditedProductById(id), HttpStatus.OK);
    }

    @DeleteMapping("/images/{fileName:.+}")
    public ResponseEntity<?> deleteImage(@PathVariable String fileName){
        fileStorageService.deleteProductImageFile(fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
