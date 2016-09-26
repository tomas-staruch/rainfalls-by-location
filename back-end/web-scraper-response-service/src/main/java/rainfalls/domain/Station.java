package rainfalls.domain;

public class Station {
	
	private Long id;
	private String name, type;
	private Position position;

	public Station(String name, Position position, String type) {
		this.name = name;
		this.position = position;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

}
