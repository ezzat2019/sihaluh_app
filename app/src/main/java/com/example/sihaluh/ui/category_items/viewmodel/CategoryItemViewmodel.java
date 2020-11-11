package com.example.sihaluh.ui.category_items.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sihaluh.data.model.CategoryItemModel;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.data.repository.CategoryItemRepo;

import java.util.List;

public class CategoryItemViewmodel extends ViewModel {
    private final MutableLiveData<List<ProductModel>> productModelListLiveData = new MutableLiveData<>();
    private final CategoryItemRepo repo;

    @ViewModelInject
    public CategoryItemViewmodel(CategoryItemRepo repo) {
        this.repo = repo;
    }

    public MutableLiveData<List<ProductModel>> getProductModelListLiveData() {
        return productModelListLiveData;
    }

    public void setProductModelListLiveData(List<ProductModel> productModelList) {
        this.productModelListLiveData.setValue(productModelList);
    }

    public void addCategory(CategoryItemModel categoryItemModel) {
        repo.setCategory(categoryItemModel);
    }

    public LiveData<CategoryItemModel> getCategoryModel(String id) {
        return repo.getCategortModel(id);
    }
}
