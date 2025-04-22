package com.electroni.store.ElectronicStoreApp.repositories;

import com.electroni.store.ElectronicStoreApp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String>
{
}
