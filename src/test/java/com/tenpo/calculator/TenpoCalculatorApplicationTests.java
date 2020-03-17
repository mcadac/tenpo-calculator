package com.tenpo.calculator;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.calculator.model.UserDto;

/**
 * Application test
 *
 * @author Milo
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TenpoCalculatorApplicationTests {

	/**
	 * Application context for testing
	 */
	@Autowired
	private WebApplicationContext webApplicationContext;

	/**
	 * Client to send request
	 */
	private MockMvc mockMvc;

	/**
	 * Object mapper for transform objects to Json
	 */
	private final ObjectMapper mapper = new ObjectMapper();

	/**
	 * Prepare the MockMvc
	 */
	@BeforeAll
	public void setup() {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

	}

	/**
	 * Create a new  user for the micro-service
	 * @throws Exception
	 */
	@Test
	void createUser() throws Exception {

		final UserDto newUser = UserDto.builder()
				.username("milo123455675")
				.password("passTest")
				.build();

		this.mockMvc.perform(
				post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writer().writeValueAsString(newUser))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

	}



}
