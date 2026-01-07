package com.sunil.cafe.cafeStore.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sunil.cafe.cafeStore.model.MenuItem;
import com.sunil.cafe.cafeStore.model.menuCategory;
import com.sunil.cafe.cafeStore.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class MenuService {


    private final MenuItemRepository menuRepository;

    @Autowired
    private Cloudinary cloudinary;


    public MenuService(MenuItemRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    public MenuItem addItem(MenuItem item, MultipartFile image) {
        item.setAvailable(true);
        String imageUrl = uploadImage(image);
        item.setImageUrl(imageUrl);
        return menuRepository.save(item);
    }


    public List<MenuItem> getMenu() {
        return menuRepository.findByAvailableTrue();
    }

    public List<MenuItem> getMenuAll() {
        return  menuRepository.findAll();
    }

    public List<MenuItem> findspecific(menuCategory category) {
        return menuRepository.findByCategoryAndAvailableTrue(category);
    }

    public void removeById(String id) {
        menuRepository.deleteById(id);
    }

    public void changeStatus(String id) {
        MenuItem item = menuRepository.findById(id).orElseThrow(()-> new RuntimeException("Menu not found"));
        boolean currentStatus=item.isAvailable();
        item.setAvailable(!currentStatus);
        menuRepository.save(item);
    }

    public String uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), ObjectUtils.emptyMap());

            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Image upload failed");
        }
    }


}
