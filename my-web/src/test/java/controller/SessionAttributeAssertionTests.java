package controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Locale;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Examples of expectations on created session attributes.
 *
 * @author Rossen Stoyanchev
 */
public class SessionAttributeAssertionTests {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new SimpleController())
                .defaultRequest(get("/"))
                .alwaysExpect(status().isOk())
                .build();
    }

    @Test
    public void testSessionAttributeEqualTo() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(request().sessionAttribute("locale", Locale.UK))
                .andExpect(request().sessionAttribute("locale", equalTo(Locale.UK)));
    }

    @Test
    public void testSessionAttributeMatcher() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(request().sessionAttribute("locale", notNullValue()));
    }


    @Controller
    @SessionAttributes("locale")
    private static class SimpleController {

        @ModelAttribute
        public void populate(Model model) {
            model.addAttribute("locale", Locale.UK);
        }

        @RequestMapping("/")
        public String handle() {
            return "view";
        }
    }

}