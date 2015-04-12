package restdemo;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static restdemo.utils.AssertUtils.assertThatRestObject;
import restdemo.objects.RestObject;
import restdemo.service.DataServiceImpl;
import restdemo.service.IDataService;
import restdemo.model.RestObjectRepository;

@RunWith(MockitoJUnitRunner.class)
public class DataServiceImplTest {

	@Mock
	private RestObjectRepository repository;

	private IDataService service;

	@Before
	public void setUp() {
		service = new DataServiceImpl(repository);
	}

	@Test
	public void testCreateNewObject() {
		RestObject obj = new RestObject();
		obj.setId("id1");
		obj.setContents("{ “name”: “value”, “name2”: “value2” }");

		when(repository.save(isA(RestObject.class))).thenAnswer(invocation -> (RestObject) invocation.getArguments()[0]);
		RestObject returnedObj = service.create(obj);
		ArgumentCaptor<RestObject> savedRestObjectArgument = ArgumentCaptor.forClass(RestObject.class);
		verify(repository, times(1)).save(savedRestObjectArgument.capture());
		verifyNoMoreInteractions(repository);

		RestObject savedRestObject = savedRestObjectArgument.getValue();
		assertThatRestObject(savedRestObject).hasContents("{ “name”: “value”, “name2”: “value2” }");
		
	}

	@Test
	public void testGetAllObjects() {
		RestObject obj = new RestObject();
		obj.setId("id1");
		obj.setContents("{ “name”: “value”, “name2”: “value2” }");
		when(repository.findAll()).thenReturn(Arrays.asList(obj));
		List<RestObject> actual = service.getAllObjects();
		assertThat(actual).hasSize(1);      
	}

	@Test
	public void testUpdateObject() {
		RestObject existing = new RestObject();
		existing.setId("id1");
		existing.setContents("{ “name3”: “value3”, “name4”: “value4” }");
		when(repository.findOne("id1")).thenReturn(existing);
		when(repository.save(existing)).thenReturn(existing);
		service.update("id1", "{ “name3”: “value3”, “name4”: “value4” }");
		verify(repository, times(1)).save(existing);
		assertThatRestObject(existing)
		.hasId("id1")
		.hasContents("{ “name3”: “value3”, “name4”: “value4” }");
	}

	@Test
	public void testDeleteObject() {
		RestObject deleted = new RestObject();
		deleted.setId("id1");
		when(repository.findOne("id1")).thenReturn(deleted);
		service.delete("id1");
		verify(repository, times(1)).delete("id1");
	}



}
