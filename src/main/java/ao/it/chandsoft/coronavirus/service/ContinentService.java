package ao.it.chandsoft.coronavirus.service;

import ao.it.chandsoft.coronavirus.exception.ResourceNotFoundException;
import ao.it.chandsoft.coronavirus.model.Continent;
import ao.it.chandsoft.coronavirus.model.Country;
import ao.it.chandsoft.coronavirus.model.ContinentWithCountries;
import ao.it.chandsoft.coronavirus.model.Statistic;
import ao.it.chandsoft.coronavirus.repository.CountryRepository;
import ao.it.chandsoft.coronavirus.util.CountryUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Nelson Chandimba da Silva
 */

@Service
public class ContinentService {
    
    @Autowired
    private CountryRepository countryRepository;
    
    public ResponseEntity listAllContinents() {
        Statistic statistic = countryRepository.listContinents();
        
        if(statistic == null) {
            throw new ResourceNotFoundException();
        }
        
        return ResponseEntity.ok(statistic);
    }
    
    public ResponseEntity<Continent> listContinentByName(String continentName) {
        Continent continent = countryRepository.listContinentByName(continentName);
        
        if(continent == null) {
            throw new ResourceNotFoundException();
        }
        
        return ResponseEntity.ok(continent);
    }

    public ResponseEntity<ContinentWithCountries> listCountriesByContinent(String continentName) {
        ContinentWithCountries continent = countryRepository.listCountriesByContinent(continentName);
        
        if(continent == null) {
            throw new ResourceNotFoundException();
        }
        
        return ResponseEntity.ok(continent);
    }

    public ResponseEntity listCountryByNameAndContinent(String countryName, String continentName) {
        Country countries = countryRepository.listCountryByNameAndContinent(countryName, continentName);
        
        if(countries == null) {
            throw new ResourceNotFoundException();
        }
        
        return ResponseEntity.ok(countries);
    }

    public HttpEntity getCountryFlag(String countryName) {
        try {
            File flag = CountryUtil.getFlag(countryName);
            byte[] image = Files.readAllBytes(flag.toPath());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(image.length);
            
            return new HttpEntity<byte[]>(image, headers);
        } catch (IOException ex) {
            throw new ResourceNotFoundException();
        }
    }
    
}