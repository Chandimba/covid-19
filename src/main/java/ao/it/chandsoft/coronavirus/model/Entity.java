package ao.it.chandsoft.coronavirus.model;

import ao.it.chandsoft.coronavirus.util.CountryUtil;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nelson Chandimba da Silva
 */

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "name", callSuper = false)
public class Entity extends GlobalEntity {
    
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String flag;
    
}