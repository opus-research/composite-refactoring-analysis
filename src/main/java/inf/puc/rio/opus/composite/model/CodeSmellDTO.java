package inf.puc.rio.opus.composite.model;

public class CodeSmellDTO {
	
	private String type; 
	private int beforeComposite;
	private int afterComposite;
	
	
	public String getType() {
		return type;
	}
	
	public int getBeforeComposite() {
		return beforeComposite;
	}
	
	public int getAfterComposite() {
		return afterComposite;
	}
	
	public void setType(String type) {
		if(this.type == null || this.type.isEmpty()) {
			this.type = type;
		}
	}
	
	
	public void setSmellBefore(int beforeComposite) {
		this.beforeComposite = beforeComposite;
	}
	
	public void setSmellAfter(int afterComposite) {
		this.afterComposite = afterComposite;
	}
	
	
	
	
	

}
