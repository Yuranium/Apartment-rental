package ru.yuriy.propertyrental.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import ru.yuriy.propertyrental.models.ApartmentSearch;
import ru.yuriy.propertyrental.models.entity.Apartment;
import ru.yuriy.propertyrental.services.ApartmentService;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/apartments")
public class ApartmentController
{
    private final ApartmentService apartmentService;
    @PostMapping("/search")
    public String searchApartments(@ModelAttribute ApartmentSearch apartmentSearch, Model model)
    {
        System.out.println(apartmentSearch);
        // todo поиск через elasticsearch
        model.addAttribute("list_apart", apartmentService.apartmentList());
        return "apartments";
    }

    @GetMapping("/add")
    public String addApartment()
    {
        return "apartments";
    }

    @PostMapping("/add")
    public String addApartment(@ModelAttribute @Valid Apartment apartment, List<MultipartFile> files, Model model)
    {
        return "redirect:/";
    }
}