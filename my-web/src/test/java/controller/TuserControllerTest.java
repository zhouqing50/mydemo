package controller;

import com.qinghuaci.controller.TuserController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Description:
 *
 * https://github.com/spring-projects/spring-test-mvc/tree/master/src/test/java/org/springframework/test/web/server/samples/standalone
 * Tuser: zhouq
 * Date: 2016/12/20
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
@ActiveProfiles("dev")
@Slf4j
public class TuserControllerTest {
    @InjectMocks
    TuserController tuserController;

    @Resource
    ServletContext context;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tuserController).build();
    }

    @Test
    public void testDubboController() throws Exception {

        mockMvc.perform(get("/tuser/get")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.tuser.id").value(1))
                .andExpect(jsonPath("$.tuser.name").value(("aaa")));
    }



    @Test
    public void tuserController() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/tuser/get")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        log.info("result={}", result);
    }

}
