package ao.it.chandsoft.coronavirus.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nelson Chandimba da Silva
 */

@NoArgsConstructor
public class ContinentWithCountries extends Continent {
    
    @Getter
    @Setter
    private List<Country> countries;

    public ContinentWithCountries(List<Country> countries) {
        super(countries.size());
        this.countries = countries;
    }
    
    public ContinentWithCountries(Continent continent, List<Country> countries) {
        this.countries = countries;
        setName(continent.getName());
        setTotalCountries(continent.getTotalCountries());
        setTotalActiveCases(continent.getTotalActiveCases());
        setTotalNewCasesInTheLast24h(continent.getTotalNewCasesInTheLast24h());
        setTotalCases(continent.getTotalCases());
        setTotalCriticalCases(continent.getTotalCriticalCases());
        setTotalNewDeathsInTheLast24h(continent.getTotalNewDeathsInTheLast24h());
        setTotalDeaths(continent.getTotalDeaths());
        setTotalRecovered(continent.getTotalRecovered());
    }
    
}
