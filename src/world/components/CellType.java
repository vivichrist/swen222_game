package world.components;

/* CONE, CUBE, DIAMOND, BALL, KEY, TICKET and TORCH should remain between
 * OUTOFBOUNDS and CHEST
 **/

public enum CellType {
	EMPTY			// 0
	, WALL			// 1
	, DOOR			// 2
	, TELEPORT		// 3
	, OPENDOOR		// 4
	, OUTOFBOUNDS	// 5
	, CONE			// 6
	, CUBE			// 7
	, DIAMOND		// 8
	, BALL			// 9
	, KEY			// 10
	, TICKET		// 11
	, TORCH			// 12
	, CHEST			// 13
	, BREIFCASE		// 14
	, DRAWERS		// 15
	, TABLE			// 16
	, BED			// 17
	, COUCH			// 18
	, PLAYER;		// 19
}