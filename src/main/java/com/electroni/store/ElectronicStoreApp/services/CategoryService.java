package com.electroni.store.ElectronicStoreApp.services;

import com.electroni.store.ElectronicStoreApp.dtoclasses.CategoryDto;
import com.electroni.store.ElectronicStoreApp.dtoclasses.PageableResponse;

import java.util.List;

public interface CategoryService
{
    //create
    CategoryDto create(CategoryDto categoryDto);
    //update
    CategoryDto update(CategoryDto categoryDto,String categoryId);
    //delete
    void delete(String categoryId );
    //get all
    //List<CategoryDto> getAllCategory();
    PageableResponse<CategoryDto>getAll(int pageNumber,int pageSize,String sortBy,String sortDir);
    //get single category
    CategoryDto getSingleCat(String categoryId);

    //search

}
