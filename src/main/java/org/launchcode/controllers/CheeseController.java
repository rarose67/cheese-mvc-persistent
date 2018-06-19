package org.launchcode.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private CategoryDao categoryDao;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese,
                                       Errors errors, @RequestParam int categoryId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/add";
        }

        Category cat = categoryDao.findOne(categoryId);
        newCheese.setCategory(cat);
        cheeseDao.save(newCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {

        for (int cheeseId : cheeseIds) {
            cheeseDao.delete(cheeseId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "category/{categoryId}", method = RequestMethod.GET)
    public String category (Model model, @PathVariable int categoryId)
    {
        List<Cheese> cheeses = categoryDao.findOne(categoryId).getCheeses();
        model.addAttribute("cheeses", cheeses);
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditForm(HttpServletRequest request, Model model, @PathVariable int cheeseId)
    {
        //String title ="Edit a Cheese";
        Cheese changedCheese;

        //model.addAttribute("title", title);
        //String error = request.getParameter("error");

        changedCheese = cheeseDao.findOne(cheeseId);

        model.addAttribute("cheese", changedCheese);
        model.addAttribute("cheeseId", cheeseId);
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("type", changedCheese.getCategory());

        return "cheese/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEditForm(@ModelAttribute  @Valid Cheese newCheese,
                                  Errors errors, @RequestParam int cheeseId, @RequestParam int categoryId, Model model)
    {
        Cheese changedCheese;

        changedCheese = cheeseDao.findOne(cheeseId);
        Category cat = categoryDao.findOne(categoryId);

        if (errors.hasErrors()) {
            model.addAttribute("cheeseId", cheeseId);
            model.addAttribute("type", cat);
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/edit";
            //return "redirect:edit/" + cheeseId;
        }

        changedCheese.setName(newCheese.getName());
        changedCheese.setDescription(newCheese.getDescription());
        changedCheese.setCategory(cat);


        cheeseDao.save(changedCheese);
        return "redirect:";
    }
}
