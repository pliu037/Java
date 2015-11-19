/**
 * Created by pengl on 2015-11-19.
 */
public class BaseballElimination {

    public BaseballElimination(String filename) {       // create a baseball division from given filename in format specified below

    }

    public int numberOfTeams() {                        // number of teams
        return 0;
    }

    public Iterable<String> teams() {                   // all teams
        return null;
    }

    public int wins(String team) {                      // number of wins for given team
        return 0;
    }

    public int losses(String team) {                    // number of losses for given team
        return 0;
    }

    public int remaining(String team) {                 // number of remaining games for given team
        return 0;
    }

    public int against(String team1, String team2) {    // number of remaining games between team1 and team2
        return 0;
    }

    public boolean isEliminated(String team) {          // is given team eliminated?
        return true;
    }

    public Iterable<String> certificateOfElimination(String team) { // subset R of teams that eliminates given team; null if not eliminated
        return null;
    }
}
