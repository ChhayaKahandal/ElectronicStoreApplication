package com.electroni.store.ElectronicStoreApp.controllers;

import com.electroni.store.ElectronicStoreApp.dtoclasses.ApiResponseMessage;
import com.electroni.store.ElectronicStoreApp.dtoclasses.CategoryDto;
import com.electroni.store.ElectronicStoreApp.dtoclasses.PageableResponse;
import com.electroni.store.ElectronicStoreApp.entities.Category;
import com.electroni.store.ElectronicStoreApp.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController
{
     @Autowired
    private CategoryService categoryService;

     //create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        //front end kdun data json mdhe yeil mhnun requestbody use krto apn.
        //service chya method call krun to data save/create kela
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        //frontend/postman la to dto form mdhe return kela.
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);

    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("categoryId") String categoryId)
    {
        CategoryDto updatedCategory = categoryService.update(categoryDto,categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable("categoryId") String categoryId)
    {
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage=ApiResponseMessage.builder().message("Category is deleted succesffulyy..!!").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue ="title",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<CategoryDto> pageableResponse=categoryService.getAll(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    //get single
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable("categoryId") String categoryId)
    {
        CategoryDto singleCatDto = categoryService.getSingleCat(categoryId);
        return new ResponseEntity<>(singleCatDto,HttpStatus.OK);
       // return ResponseEntity.ok(singleCatDto);//this is the second way of above line.status ok asel tr ok(),cretated asel tr created().
    }


}
