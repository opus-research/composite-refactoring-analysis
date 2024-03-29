package inf.puc.rio.opus.composite.model.effect;

public class CodeSmellDTO {
	
	private String type; 
	private int beforeComposite;
	private int afterComposite;
	private int addedSmells;
	private int removedSmells;
	private int notAffectSmells;
	private String id;
	
	
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

	public int getAddedSmells() {
		return addedSmells;
	}

	public void setAddedSmells(int addedSmells) {
		this.addedSmells = addedSmells;
	}

	public int getRemovedSmells() {
		return removedSmells;
	}

	public void setRemovedSmells(int removedSmells) {
		this.removedSmells = removedSmells;
	}

	public int getNotAffectSmells() {
		return notAffectSmells;
	}

	public void setNotAffectSmells(int notAffectSmells) {
		this.notAffectSmells = notAffectSmells;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
