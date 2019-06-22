package icfp2019

/*
A solution for a task
  prob-NNN.desc
is a sequence of actions encoded as a single-line text file named
  prob-NNN.sol
for the corresponding numberNNN.
The actions are encoded as follows:
  action ::=
      W(move up)
      | S(move down)
      | A(move left)
      | D(move right)
      | Z(do nothing)
      | E(turn manipulators 90°clockwise)
      | Q(turn manipulators 90°counterclockwise)
      | B(dx,dy)(attach a new manipulator with relative coordinates(dx,dy))
      | F(attach fast wheels)
      | L(start using a drill)

  solution ::= rep(action)

  A solution isvalid, if it does not force the worker-wrapper to go through the walls and obstacles
  (unless it uses a drill), respects the rules of using boosters, and, upon finishing,
  leaves all reachablesquares of the map wrapped.
 */
sealed class Action

object MoveUp : Action()
object MoveDown : Action()
object MoveLeft : Action()
object MoveRight : Action()
object DoNothing : Action()
object TurnClockwise : Action()
object TurnCounterClockwise : Action()
data class AttachManipulator(val point: Point) : Action()
object AttachFastWheels : Action()
object StartDrill : Action()
object PlantTeleportResetPoint : Action()
data class TeleportBack(val targetResetPoint: Point) : Action()
object CloneRobot : Action()

fun toString(action: Action): String = when (action) {
    MoveUp -> "W"
    MoveDown -> "S"
    MoveLeft -> "A"
    MoveRight -> "D"
    DoNothing -> "Z"
    TurnClockwise -> "E"
    TurnCounterClockwise -> "Q"
    is AttachManipulator -> "A(${action.point.x}, ${action.point.y})"
    AttachFastWheels -> "F"
    StartDrill -> "L"
    PlantTeleportResetPoint -> "R"
    is TeleportBack -> "T(${action.targetResetPoint.x},${action.targetResetPoint.x})"
    CloneRobot -> "C"
}

fun encodeActions(actions: Iterable<Action>): String =
    actions.joinToString(separator = "") { toString(it) }

fun encodeRobotActions(robotActions: Map<RobotId, List<Action>>): String =
    robotActions.entries.sortedBy { it.key.id }
        .joinToString(separator = "#") { encodeActions(it.value) }
