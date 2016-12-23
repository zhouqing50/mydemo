package controller;

import model.Person;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Tests with view resolution.
 *
 * @author Rossen Stoyanchev
 */
public class ViewResolutionTests {

    @Test
    public void testJspOnly() throws Exception {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".jsp");

        standaloneSetup(new PersonController()).setViewResolvers(viewResolver).build()
                .perform(get("/person/Corea"))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("person"))
                .andExpect(forwardedUrl("/WEB-INF/person/show.jsp"));
    }

//    @Test
//    public void testJsonOnly() throws Exception {
//
//        standaloneSetup(new PersonController()).setSingleView(new MappingJacksonJsonView()).build()
//                .perform(get("/person/Corea"))
//                .andExpect(status().isOk())
//                .andExpect(content().mimeType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.person.name").value("Corea"));
//    }
//
//    @Test
//    public void testXmlOnly() throws Exception {
//
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        marshaller.setClassesToBeBound(Person.class);
//
//        standaloneSetup(new PersonController()).setSingleView(new MarshallingView(marshaller)).build()
//                .perform(get("/person/Corea"))
//                .andExpect(status().isOk())
//                .andExpect(content().mimeType(MediaType.APPLICATION_XML))
//                .andExpect(xpath("/person/name/text()").string(equalTo("Corea")));
//    }
//
//    @Test
//    public void testContentNegotiation() throws Exception {
//
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        marshaller.setClassesToBeBound(Person.class);
//
//        List<View> viewList = new ArrayList<View>();
//        viewList.add(new MappingJacksonJsonView());
//        viewList.add(new MarshallingView(marshaller));
//
//        ContentNegotiatingViewResolver cnViewResolver = new ContentNegotiatingViewResolver();
//        cnViewResolver.setDefaultViews(viewList);
//        cnViewResolver.setDefaultContentType(MediaType.TEXT_HTML);
//
//        MockMvc mockMvc =
//                standaloneSetup(new PersonController())
//                        .setViewResolvers(cnViewResolver, new InternalResourceViewResolver())
//                        .build();
//
//        mockMvc.perform(get("/person/Corea"))
//                .andExpect(status().isOk())
//                .andExpect(model().size(1))
//                .andExpect(model().attributeExists("person"))
//                .andExpect(forwardedUrl("person/show"));
//
//        mockMvc.perform(get("/person/Corea").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().mimeType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.person.name").value("Corea"));
//
//        mockMvc.perform(get("/person/Corea").accept(MediaType.APPLICATION_XML))
//                .andExpect(status().isOk())
//                .andExpect(content().mimeType(MediaType.APPLICATION_XML))
//                .andExpect(xpath("/person/name/text()").string(equalTo("Corea")));
//    }

    @Test
    public void defaultViewResolver() throws Exception {

        standaloneSetup(new PersonController()).build()
                .perform(get("/person/Corea"))
                .andExpect(model().attribute("person", hasProperty("name", equalTo("Corea"))))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("person/show"));  // InternalResourceViewResolver
    }


    @Controller
    private static class PersonController {

        @RequestMapping(value="/person/{name}", method=RequestMethod.GET)
        public String show(@PathVariable String name, Model model) {
            Person person = new Person(name);
            model.addAttribute(person);
            return "person/show";
        }
    }

}
