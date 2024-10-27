package ru.yuriy.propertyrental.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yuriy.propertyrental.models.ApartmentForm;
import ru.yuriy.propertyrental.models.ApartmentSearch;
import ru.yuriy.propertyrental.services.ApartmentService;
import ru.yuriy.propertyrental.util.ApartmentValidator;


@Controller
@RequiredArgsConstructor
@RequestMapping("/apartments")
public class ApartmentController
{
    private final ApartmentService apartmentService;

    private final ApartmentValidator apartmentValidator;

    @GetMapping("/all")
    public String allApartments(Model model)
    {
        model.addAttribute("listApartments", apartmentService.apartmentList());
        return "allApartments";
    }

    @PostMapping("/search")
    public String searchApartments(@ModelAttribute ApartmentSearch apartmentSearch, Model model)
    {
        System.out.println(apartmentSearch);
        // todo поиск через elasticsearch
        model.addAttribute("list_apart", apartmentService.apartmentList());
        return "apartments";
    }

    @GetMapping("/add")
    public String addApartment(Model model)
    {
        model.addAttribute("apartmentForm", new ApartmentForm());
        return "addApartment";
    }

    @PostMapping("/add")
    public String addApartment(@ModelAttribute @Valid ApartmentForm apartment, BindingResult result, Model model)
    {
        apartmentValidator.validate(apartment, result);
        model.addAttribute("images", result);
        if (result.hasErrors())
            return "addApartment";
        apartmentService.saveApartment(apartment);
        return "redirect:/apartments/all";
    }
}