package ru.yuriy.propertyrental.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yuriy.propertyrental.models.ApartmentSearch;
import ru.yuriy.propertyrental.services.ApartmentService;


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
}