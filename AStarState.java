import java.util.*;


/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;

    private HashMap<Location, Waypoint> openedWaypoint = new HashMap<>();

    private HashMap<Location, Waypoint> closedWaypoint = new HashMap<>();


    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if (numOpenWaypoints() == 0) {
            // если нет открытых точек - возвращаем null
            return null;
        }
        // инициализируем набор, который будет содержать все наши ключи
        Set openWPKeys = openedWaypoint.keySet();
        // итератор, чтобы пробежать по всему набору ключей
        Iterator i = openWPKeys.iterator();
        // переменная для хранения лучшей точки
        Waypoint bestPoint = null;
        // лучшая стоимость
        float bestCost = Float.MAX_VALUE;

        while (i.hasNext()) {
            // берем текущую точку по ключу
            Location loc = (Location)i.next();
            Waypoint wp = openedWaypoint.get(loc);
            float wpCost = wp.getTotalCost();
                // если для этой точки стоимость меньше лучшей стоимости, то ее значения помещаем в лучшие
            if (wpCost < bestCost) {
                bestPoint = openedWaypoint.get(loc);
                bestCost = wpCost;
            }
        }
        // возвращаем лучшую точку
        return bestPoint;

    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one <em>only
     * if</em> the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        // TODO:  Implement.
        // получаем вершину для данного местополоржения по ключу, если ее нет - возвращаем null
        Waypoint oldWp = this.openedWaypoint.getOrDefault(newWP.getLocation(), null);
        if (oldWp == null) {
            // если для данного местоположения(ключа) нет вершины - добавляем ее
            this.openedWaypoint.put(newWP.getLocation(), newWP);
            // возвращаем true - новая вершина успешно добавлена в набор
            return true;
        } else {
            // вершина имеется дла данного местоположения - проверяем стоимости пути
            if (newWP.getPreviousCost() < oldWp.getPreviousCost()) {
                // если новая стоимость меньше старой - заменяем старую точку и возвращаем true
                this.openedWaypoint.put(newWP.getLocation(), newWP);
                return true;
            } else {
                // если стоимость новой точки не меньше старой - ничего не меняем и возвращаем false
                return false;
            }
        }
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        // TODO:  Implement.
        return this.openedWaypoint.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        // TODO:  Implement.
        // обавляем по указанному местоположению открытую точку в закрытую
        this.closedWaypoint.put(loc, this.openedWaypoint.get(loc));
        // удаляем вершину данного местоположения из открытых
        this.openedWaypoint.remove(loc);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        // TODO:  Implement.
        // возвращаем true, если по данному местоположению есть точка
        return this.closedWaypoint.containsKey(loc);
    }
}
