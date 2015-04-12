package restdemo.objects;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author leonardlu
 * Wrapper Object for Plain JSON strings
 *
 */

public class RestObject {
	@Id private String id;
	private String contents;
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
}
