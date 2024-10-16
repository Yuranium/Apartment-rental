package ru.yuriy.propertyrental.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yuriy.propertyrental.models.ApartmentSearch;


@Controller
@RequestMapping("/apartments")
public class ApartmentController
{
    @PostMapping("/search")
    public String searchApartments(ApartmentSearch apartmentSearch)
    {
        // todo поиск через elasticsearch
        return "apartments";
    }
}