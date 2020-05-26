package com.ranjay.directory.web.controller;

import javax.validation.Valid;

import com.ranjay.directory.model.Business;
import com.ranjay.directory.repository.BusinessRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class HomeController {

    @Autowired
    private BusinessRepository businessRepository;
    
    @GetMapping(value="/")
    public String home(@RequestParam(defaultValue = "0") int page, @RequestParam( defaultValue = "",name = "name") String name,Model model) {

       Page<Business> businesses = businessRepository.findByNameLike(name, PageRequest.of(page, 10));

        model.addAttribute("business", businessRepository.findAll());
        
        return "index";
    }

    @GetMapping(value="/register")
    public String registerBusines(Model model) {

        model.addAttribute("business", new Business());
        return "views/addBusiness";
    }

    @PostMapping(value="/register")
    public String addBusiness(@Valid @ModelAttribute("business") Business business, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "views/addBusiness";
        }
        
        businessRepository.save(business);

        return "redirect:/";
    }
    
    
    
}