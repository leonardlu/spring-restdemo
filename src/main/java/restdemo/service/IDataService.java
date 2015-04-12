package restdemo.service;

import java.util.List;

import restdemo.objects.RestObject;

/**
 * 
 * @author leonardlu
 *
 */

public interface IDataService {
	
	public List<RestObject> getAllObjects();
	
	public RestObject create(RestObject obj);
	
	public void delete(String id);
	
	public RestObject update(String id, String str);

	public RestObject findById(String id);

}
