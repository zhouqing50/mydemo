package controller;

import model.Person;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Tests with {@link Filter}'s.
 *
 * @author Rob Winch
 */
public class FilterTests {

    @Test
    public void whenFiltersCompleteMvcProcessesRequest() throws Exception {
        standaloneSetup(new PersonController())
                .addFilters(new ContinueFilter()).build()
                .perform(post("/persons").param("name", "Andy"))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/person/1"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("id"))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attribute("message", "success!"));
    }

    @Test
    public void filtersProcessRequest() throws Exception {
        standaloneSetup(new PersonController())
                .addFilters(new ContinueFilter(), new RedirectFilter()).build()
                .perform(post("/persons").param("name", "Andy"))
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void filterMappedBySuffix() throws Exception {
        standaloneSetup(new PersonController())
                .addFilter(new RedirectFilter(), "*.html").build()
                .perform(post("/persons.html").param("name", "Andy"))
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void filterWithExactMapping() throws Exception {
        standaloneSetup(new PersonController())
                .addFilter(new RedirectFilter(), "/p", "/persons").build()
                .perform(post("/persons").param("name", "Andy"))
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void filterSkipped() throws Exception {
        standaloneSetup(new PersonController())
                .addFilter(new RedirectFilter(), "/p", "/person").build()
                .perform(post("/persons").param("name", "Andy"))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/person/1"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("id"))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attribute("message", "success!"));
    }

    @Test
    public void filterWrapsRequestResponse() throws Exception {
        standaloneSetup(new PersonController())
                .addFilters(new WrappingRequestResponseFilter()).build()
                .perform(post("/user"))
                .andExpect(model().attribute("principal", WrappingRequestResponseFilter.PRINCIPAL_NAME));
    }


    @Controller
    private static class PersonController {
        @RequestMapping(value = "/persons", method = RequestMethod.POST)
        public String save(Person person, Errors errors, RedirectAttributes redirectAttrs) {
            if (errors.hasErrors()) {
                return "person/add";
            }
            redirectAttrs.addAttribute("id", "1");
            redirectAttrs.addFlashAttribute("message", "success!");
            return "redirect:/person/{id}";
        }

        @RequestMapping(value = "/user")
        public ModelAndView user(Principal principal) {
            return new ModelAndView("user/view", "principal", principal.getName());
        }

        @RequestMapping(value = "/forward")
        public String forward() {
            return "forward:/persons";
        }
    }

    private class ContinueFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            filterChain.doFilter(request, response);
        }
    }

    private static class WrappingRequestResponseFilter extends OncePerRequestFilter {

        public static final String PRINCIPAL_NAME = "WrapRequestResponseFilterPrincipal";

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            filterChain.doFilter(new HttpServletRequestWrapper(request) {
                @Override
                public Principal getUserPrincipal() {
                    return new Principal() {
                        public String getName() {
                            return PRINCIPAL_NAME;
                        }
                    };
                }
            }, new HttpServletResponseWrapper(response));
        }
    }

    private class RedirectFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            response.sendRedirect("/login");
        }
    }
}