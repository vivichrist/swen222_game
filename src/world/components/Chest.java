package world.components;

public class Chest extends Container{

	public Chest(String name){
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