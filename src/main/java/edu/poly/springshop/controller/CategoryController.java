package edu.poly.springshop.controller;

import edu.poly.springshop.domain.Category;
import edu.poly.springshop.dto.CategoryDto;
import edu.poly.springshop.exception.CategoryExcepion;
import edu.poly.springshop.service.CategoryService;
import edu.poly.springshop.service.MapValidationErrorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto dto,
                                             BindingResult result
                                             ){

            ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);

            if(responseEntity != null){
                return responseEntity;
            }

            Category entity = new Category();
            BeanUtils.copyProperties(dto, entity);

            entity = categoryService.save(entity);
            dto.setId(entity.getId());

            return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id")Long id,@Valid @RequestBody CategoryDto dto,
    BindingResult result
    ){

        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);

        if(responseEntity != null){
            return responseEntity;
        }

        Category entity = new Category();
        BeanUtils.copyProperties(dto, entity);

        entity = categoryService.update(id,entity);
        dto.setId(entity.getId());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getCategories (){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> getCategories (
            @PageableDefault(size = 5, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable){
        return new ResponseEntity<>(categoryService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<?> getCategories(@PathVariable("id")Long id){
            return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK) ;
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id")Long id){
        categoryService.deleteById(id);
        return new ResponseEntity<>("Category width id: " + id + "was deleted", HttpStatus.OK) ;
    }


}
