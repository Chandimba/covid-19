package ao.it.chandsoft.coronavirus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nelson Chandimba da Silva
 */

@AllArgsConstructor
@NoArgsConstructor
public class Country extends Entity {
    
    @Getter
    @Setter
    private String continent;
    
}
