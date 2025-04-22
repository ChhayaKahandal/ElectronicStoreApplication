package com.electroni.store.ElectronicStoreApp.services.impl;

import com.electroni.store.ElectronicStoreApp.dtoclasses.CategoryDto;
import com.electroni.store.ElectronicStoreApp.dtoclasses.PageableResponse;
import com.electroni.store.ElectronicStoreApp.entities.Category;
import com.electroni.store.ElectronicStoreApp.exception.ResourceNotFoundException;
import com.electroni.store.ElectronicStoreApp.helper.Helper;
import com.electroni.store.ElectronicStoreApp.repositories.CategoryRepository;
import com.electroni.store.ElectronicStoreApp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelmapper;
    @Override
    public CategoryDto create(CategoryDto categoryDto)
    {
        //creating category Id randomly/manually
        String categoId= UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoId);
        //controller kdun dto milele tyala entity mdhe convert krun save kel.
        Category category = modelmapper.map(categoryDto, Category.class);
        Category savedcategory=categoryRepository.save(category);
        //ani nantr prt tya entity la dto mdhe convert krun controller la return kela.
        CategoryDto categoryDto1 = modelmapper.map(savedcategory, CategoryDto.class);
        return categoryDto1;
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId)
    {
        //get category of given id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id!!"));

        //update category details
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        //then save that new detail
        Category updatedCategoryted = categoryRepository.save(category);
        //covert the entity into dto and return dto to controller
        return modelmapper.map(updatedCategoryted,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId)
    {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with given id!!"));
        categoryRepository.delete(category);

    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir)
    {
        //create object of sort.jar sortDir mdhe desc asel tr descending ne sorting kar otherwise ascending ne kr.
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        //cretae object of pageable
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        //this return page of category
        Page<Category>page=categoryRepository.findAll(pageable);
        //here we calling our Helper class method which holds all logic for pagination.
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;//here we returning the category dto's pageable response.
    }

    @Override
    public CategoryDto getSingleCat(String categoryId)
    {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with given id!!"));
        return modelmapper.map(category,CategoryDto.class);
    }


}
