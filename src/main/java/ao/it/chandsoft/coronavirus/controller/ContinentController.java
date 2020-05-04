package ao.it.chandsoft.coronavirus.controller;

import ao.it.chandsoft.coronavirus.model.Continent;
import ao.it.chandsoft.coronavirus.model.ContinentWithCountries;
import ao.it.chandsoft.coronavirus.model.Statistic;
import ao.it.chandsoft.coronavirus.service.ContinentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nelson Chandimba da Silva
 */

@Api(value = "/v1/continents", description = "The resources available to get the global statistics report about Covid-16")
@RestController
@RequestMapping("v1/continents")
public class ContinentController {
    
    @Autowired
    private ContinentService countryService;
    
    @ApiOperation(value = "show global statistics report about Covid-16 around of the world and group it by continent")
    @GetMapping
    public ResponseEntity<Statistic> listAllContinents() {
        return countryService.listAllContinents();
    }
    
    @ApiOperation(value = "show global statistics report about Covid-16 by only one  continent")
    @ApiImplicitParams(@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "continentName", dataTypeClass = String.class))
    @GetMapping("{continentName}")
    public ResponseEntity<Continent> listByContinent(@PathVariable("continentName") String continentName) {
        return countryService.listContinentByName(continentName);
    }
    
    @ApiOperation(value = "group the global statistics about Covid-16 by continent and country")
    @ApiImplicitParams(@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "continentName", dataTypeClass = String.class))
    @GetMapping("{continentName}/countries")
    public ResponseEntity<ContinentWithCountries> listCountriesByContinent(@PathVariable("continentName") String continentName) {
        return countryService.listCountriesByContinent(continentName);
    }
    
    @ApiOperation(value = "show global statistics report about Covid-16 around by continent and country")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "continentName", dataTypeClass = String.class, 
                    allowableValues = "all, africa, asia, europe, oceania, north america, south america"),
            @ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "countryName", dataTypeClass = String.class)})
    @GetMapping("{continentName}/countries/{countryName}")
    public ResponseEntity<ContinentWithCountries> listByContinentAndCountry(
            @PathVariable("continentName") String continentName,
            @PathVariable("countryName") String countryName) {
        return countryService.listCountryByNameAndContinent(countryName, continentName);
    }
    
    @ApiOperation(value = "return the country flag")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "countryName", dataTypeClass = String.class)})
    @ApiResponse(code = 200, message = "", examples = @Example(@ExampleProperty(value = "content-type", mediaType = "image/png")))
    @GetMapping("all/countries/{countryName}/flag")
    public HttpEntity<byte[]> getCountryFlag(@PathVariable("countryName") String countryName) {
        return countryService.getCountryFlag(countryName);
    }
}