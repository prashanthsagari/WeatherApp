package com.weather.app.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.weather.app.service.impl.BookmarkServiceImpl;

@ExtendWith(MockitoExtension.class)
class BookmarkControllerTest {

	@Mock
	BookmarkServiceImpl bookmarkService;

	private MockMvc mockMvc;

	private Document document;

	private List<Document> listOfDocuments;

	@InjectMocks
	private BookmarkController bookmarkController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(bookmarkController).build();
		document = new Document();
		listOfDocuments = new ArrayList<>();
		document.put("username", "prashanth");
		document.put("favorites", List.of(new Document("city", "Dharmavaram"), new Document("city", "Hyderabad")));
		listOfDocuments.add(document);

	}

	@Test
	public void testPing() throws Exception {
		mockMvc.perform(get("/bookmark/ping")).andExpect(status().isOk())
				.andExpect(content().string("Bookmark service is UP"));
	}

	@Test
	public void testBookmarks() throws Exception {
		when(bookmarkService.getAllBookmarks()).thenReturn(listOfDocuments);
		mockMvc.perform(get("/bookmark/all-bookmarks")).andExpect(status().isOk());
		Mockito.verify(bookmarkService).getAllBookmarks();
	}

}
