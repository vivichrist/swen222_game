package world.components;

public class Briefcase extends Container{

	public Briefcase(String name) {
		super(name);
	}

	@Override
	public boolean setContents(MoveableObject contents) {
		if(super.contents != null){
			return false;
		}
		else{
			super.contents = contents;
			return true;
		}
	}

	@Override
	public MoveableObject getContents() {
		return super.contents;
	}

}
