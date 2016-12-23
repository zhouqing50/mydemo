package controller;

import model.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Redirect scenarios including saving and retrieving flash attributes.
 *
 * @author Rossen Stoyanchev
 */
public class RedirectTests {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new PersonController()).build();
    }

    @Test
    public void save() throws Exception {
        this.mockMvc.perform(post("/persons").param("name", "Andy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/persons/Joe"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("name"))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attribute("message", "success!"));
    }

    @Test
    public void saveWithErrors() throws Exception {
        this.mockMvc.perform(post("/persons"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("persons/add"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("person"))
                .andExpect(flash().attributeCount(0));
    }

    @Test
    public void getPerson() throws Exception {
        this.mockMvc.perform(get("/persons/Joe").flashAttr("message", "success!"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("persons/index"))
                .andExpect(model().size(2))
                .andExpect(model().attribute("person", new Person("Joe")))
                .andExpect(model().attribute("message", "success!"))
                .andExpect(flash().attributeCount(0));
    }


    @Controller
    private static class PersonController {

        @RequestMapping(value="/persons/{name}", method=RequestMethod.GET)
        public String getPerson(@PathVariable String name, Model model) {
            model.addAttribute(new Person(name));
            return "persons/index";
        }

        @RequestMapping(value="/persons", method=RequestMethod.POST)
        public String save(Person person, Errors errors, RedirectAttributes redirectAttrs) {
            if (errors.hasErrors()) {
                return "persons/add";
            }
            redirectAttrs.addAttribute("name", "Joe");
            redirectAttrs.addFlashAttribute("message", "success!");
            return "redirect:/persons/{name}";
        }
    }
}