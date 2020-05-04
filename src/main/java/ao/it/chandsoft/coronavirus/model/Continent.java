package ao.it.chandsoft.coronavirus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author chandimba
 */

@AllArgsConstructor
@NoArgsConstructor
public class Continent extends Entity {
    
    @Getter
    @Setter
    private Integer totalCountries;
    
    public Continent(Entity entity) {
        setName(entity.getName());
        setTotalActiveCases(entity.getTotalActiveCases());
        setTotalNewCasesInTheLast24h(entity.getTotalNewCasesInTheLast24h());
        setTotalCases(entity.getTotalCases());
        setTotalCriticalCases(entity.getTotalCriticalCases());
        setTotalNewDeathsInTheLast24h(entity.getTotalNewDeathsInTheLast24h());
        setTotalDeaths(entity.getTotalDeaths());
        setTotalRecovered(entity.getTotalRecovered());
    }
}
