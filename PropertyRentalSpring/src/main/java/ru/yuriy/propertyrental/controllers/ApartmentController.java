package ru.yuriy.propertyrental.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yuriy.propertyrental.models.ApartmentForm;
import ru.yuriy.propertyrental.models.ApartmentSearch;
import ru.yuriy.propertyrental.models.entity.Apartment;
import ru.yuriy.propertyrental.services.ApartmentESService;
import ru.yuriy.propertyrental.services.ApartmentService;
import ru.yuriy.propertyrental.services.ServiceApService;
import ru.yuriy.propertyrental.util.validators.ApartmentValidator;

import java.security.Principal;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/apartments")
public class ApartmentController
{
    private final ApartmentService apartmentService;

    private final ApartmentESService apartmentESService;

    private final ServiceApService serviceApService;

    private final ApartmentValidator apartmentValidator;

    @GetMapping("/all")
    public String allApartments(HttpSession session, Model model)
    {
        List<Apartment> apartments = apartmentService.apartmentList();
        session.setAttribute("apartmentsSearchResult", apartments);
        model.addAttribute("list_apart", apartments);
        return "apartments";
    }

    @PostMapping("/search")
    public String searchApartments(@ModelAttribute ApartmentSearch apartmentSearch, HttpSession session, Model model)
    {
        List<Apartment> apartments;
        if (apartmentSearch.isEmptySearch())
            apartments = apartmentService.apartmentList();
        else
        {
            apartments = apartmentService.getSearchApartment(apartmentSearch);
            System.out.println(apartments);
        }
        session.setAttribute("apartmentsSearchResult", apartments);
        model.addAttribute("list_apart", apartments);
        return "apartments";
    }

    @PostMapping("/sort")
    public String sortApartments(@ModelAttribute Sorting sorting, HttpSession session, Model model)
    {
        model.addAttribute("list_apart", apartmentService.sortApartment(sorting, session));
        return "apartments";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_OWNER') || hasAuthority('ROLE_ADMIN')")
    public String addApartment(Model model)
    {
        model.addAttribute("apartmentForm", new ApartmentForm());
        model.addAttribute("services", serviceApService.getAllServices());
        return "addApartment";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_OWNER') || hasAuthority('ROLE_ADMIN')")
    public String addApartment(@ModelAttribute @Valid ApartmentForm apartment, BindingResult result,
                               Model model, Principal principal)
    {
        apartmentValidator.validate(apartment, result);
        model.addAttribute("images", result);
        if (result.hasErrors())
        {
            model.addAttribute("services", serviceApService.getAllServices());
            return "addApartment";
        }
        apartmentService.saveApartment(apartment, principal);
        return "redirect:/apartments/all";
    }

    @GetMapping("/{id}")
    public String apartmentProfile(@PathVariable Long id, Model model, Principal principal)
    {
        Apartment apartment = apartmentService.findApartmentById(id);
        model.addAttribute("apartment", apartment);
        model.addAttribute("isAlreadyHere",
                apartmentService.isApartmentBooked(apartment.getId(), principal));
        return "apartmentProfile";
    }

    @ResponseBody
    @GetMapping("/api/autocomplete")
    public List<String> autocomplete(@RequestParam(name = "q", required = false) String query)
    {
        return apartmentESService.autocompleteQuery(query);
    }

    @Getter
    @Setter
    public static class Sorting
    {
        private boolean squareSort;
        private boolean roomsSort;
        private boolean priceSort;
    }
}