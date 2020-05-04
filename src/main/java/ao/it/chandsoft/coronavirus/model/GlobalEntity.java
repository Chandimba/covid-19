package ao.it.chandsoft.coronavirus.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chandimba
 */

@Getter
@Setter
public class GlobalEntity {
    public Integer totalCases;
    public Integer totalNewCasesInTheLast24h;
    public Integer totalDeaths;
    public Integer totalNewDeathsInTheLast24h;
    public Integer totalRecovered;
    public Integer totalActiveCases;
    public Integer totalCriticalCases;
}
