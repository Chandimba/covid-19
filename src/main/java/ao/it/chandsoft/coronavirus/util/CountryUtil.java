package ao.it.chandsoft.coronavirus.util;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author chandimba
 */

@Configuration
@PropertySource("classpath:/countries.properties")
public class CountryUtil {
    
    @Autowired
    private Environment env;
    @Autowired
    private HttpServletRequest request;
    
    private static final File FLAGS = new File("bandeiras/");
    private static final File DEFAULT_FLAG = new File("bandeiras/world.png");
    
    public String getContinent(String countryName) {
        String continent = env.getProperty(countryName.replace(' ', '_'));
        return continent != null? continent: "Unknown";
    }
    
    public static File getFlag(String countryName) {
        
        for(File flag: FLAGS.listFiles()) {
            if(flag.getName().equalsIgnoreCase(countryName + ".png")) {
                return flag;
            }
        }
        
        return DEFAULT_FLAG;
    }
    
    public String getFlagUrl(String countryName) {
        String flag = request.getRequestURL().toString();
        return flag.substring(0, flag.indexOf("continents") + 10) + "/all/countries/" + countryName.replace(' ', '_') + "/flag";
    }
}
