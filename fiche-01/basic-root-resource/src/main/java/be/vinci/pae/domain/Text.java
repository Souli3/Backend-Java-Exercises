package be.vinci.pae.domain;

public class Text {
	
	private int id;
	private String content;
	public enum Level{EASY("easy"),MEDIUM("medium"),HARD("hard");
		private String level;
		
		Level(String level){
			this.level=level;
		}
		
		public String getLevel1() {
			return this.level;
		}
	};
	private String level;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Text other = (Text) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Text [id=" + id + ", content=" + content + ", level=" + level + "]";
	}
	
	
	

}
