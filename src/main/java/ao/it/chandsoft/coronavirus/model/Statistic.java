package ao.it.chandsoft.coronavirus.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;


/**
 * @author Nelson Chandimba da Silva
 */

public class Statistic extends GlobalEntity{
    
    @Getter
    @Setter
    private Integer totalContinents;
    @Getter
    @Setter
    private List<Continent> continents;

    public Statistic(Continent world, List<Continent> continents) {
        this.totalContinents = continents.size();
        this.continents = continents;
        
        setTotalActiveCases(world.getTotalActiveCases());
        setTotalNewCasesInTheLast24h(world.getTotalNewCasesInTheLast24h());
        setTotalCases(world.getTotalCases());
        setTotalCriticalCases(world.getTotalCriticalCases());
        setTotalNewDeathsInTheLast24h(world.getTotalNewDeathsInTheLast24h());
        setTotalDeaths(world.getTotalDeaths());
        setTotalRecovered(world.getTotalRecovered());
    }
    
}
