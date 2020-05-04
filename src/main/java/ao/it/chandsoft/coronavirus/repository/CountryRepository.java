package ao.it.chandsoft.coronavirus.repository;

import ao.it.chandsoft.coronavirus.exception.CoronavirusException;
import ao.it.chandsoft.coronavirus.model.Continent;
import ao.it.chandsoft.coronavirus.model.ContinentWithCountries;
import ao.it.chandsoft.coronavirus.model.Country;
import ao.it.chandsoft.coronavirus.model.Entity;
import ao.it.chandsoft.coronavirus.model.Statistic;
import ao.it.chandsoft.coronavirus.util.CountryUtil;
import ao.it.chandsoft.coronavirus.util.UrlUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Nelson Chandimba da Silva
 */
@Repository
public class CountryRepository {

    @Autowired
    private CountryUtil countryUtil;
    private static final String URL = "https://www.worldometers.info/coronavirus/#countries";

    private static final List<String> CONTINENTS;

    static {
        CONTINENTS = Arrays.asList("Africa", "Asia", "Europe", "Oceania", "North America", "South America");
    }

    public Statistic listContinents() {
        List<Country> countries = listAll();
        List<Country> continentsFiltered = countries.stream()
                .filter(country -> CONTINENTS.contains(country.getName()))
                .collect(Collectors.toList());

        List<Continent> continents = transformToContinentList(continentsFiltered);
        continents.forEach(continent -> addTotalCountriesToContinent(continent, countries));

        Country world = countries.stream()
                .filter(country -> country.getName().equalsIgnoreCase("World"))
                .findFirst()
                .get();

        Statistic statistic = new Statistic(transformToContinent(world), continents);

        return statistic;
    }

    public Continent listContinentByName(String continentName) {
        
        if(continentName == null || !CONTINENTS.stream().anyMatch(continent -> continentName.equalsIgnoreCase(continent))) {
            return null;
        }
        
        List<Country> countries = listAll().stream()
                .filter(country -> country.getContinent().equalsIgnoreCase(continentName) || country.getName().equalsIgnoreCase(continentName))
                .collect(Collectors.toList());

        Country continentFound = countries.stream()
                .filter(country -> country.getName().equalsIgnoreCase(continentName))
                .findFirst()
                .get();
        
        countries.remove(continentFound);
        Continent continent = transformToContinent(continentFound);
        addTotalCountriesToContinent(continent, countries);
        return continent;
    }

    public ContinentWithCountries listCountriesByContinent(String continentName) {
        
        if(continentName == null || !CONTINENTS.stream().anyMatch(continent -> continentName.equalsIgnoreCase(continent))) {
            if(!continentName.trim().equalsIgnoreCase("all")){
                return null;
            }
        }
        
        final String continentNameTrimed = continentName.trim().equalsIgnoreCase("all") ? "World": continentName.trim();
        
        List<Country> countries = listAll();
        List<Country> countriesFound = null;
        
        if (continentNameTrimed.equalsIgnoreCase("World")) {
            countriesFound = countries.stream()
                .filter(country -> !CONTINENTS.stream().anyMatch(continent -> country.getName().equalsIgnoreCase(continent)))
                .collect(Collectors.toList());
            
        } else {
            countriesFound = countries.stream()
                .filter(country -> country.getContinent().equalsIgnoreCase(continentNameTrimed) || country.getName().equalsIgnoreCase(continentNameTrimed))
                .collect(Collectors.toList());
        }
        
        return transformToContinentAux(continentNameTrimed, countriesFound);
    }

    public Country listCountryByNameAndContinent(String countryName, String continentName) {
        try {
            
            return listAll().stream()
                    .filter(country -> continentName.equalsIgnoreCase("all")? country.getName().equalsIgnoreCase(countryName): country.getContinent().equalsIgnoreCase(continentName) && country.getName().equalsIgnoreCase(countryName))
                    .findFirst()
                    .get();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Country> listAll() throws CoronavirusException {
        try {
            Response response = UrlUtils.executeUrl(URL);
            if (response.isSuccessful()) {
                Set<Country> countries = new HashSet<>();
                try (BufferedReader buffer = new BufferedReader(new InputStreamReader(response.body().byteStream(), "UTF-8"))) {
                    final StringBuilder stringBuilder = new StringBuilder();
                    buffer.lines().forEach(row -> {
                        stringBuilder.append(row);
                    });

                    Document document = Jsoup.parse(stringBuilder.toString());
                    Elements tableRows = document.getElementsByTag("tr");

                    tableRows.forEach(element -> {
                        Elements tableData = element.getElementsByTag("td");
                        Country country = createCountry(tableData);
                        if (country != null) {
                            country.setFlag(countryUtil.getFlagUrl(country.getName()));
                            countries.add(country);
                        }
                    });
                }

                return new ArrayList<>(countries);
            }

        } catch (IOException ex) {
            throw new CoronavirusException("Internal error server. Please, try again later");
        }

        return null;
    }

    int column;

    private Country createCountry(Elements tableData) {
        column = 0;
        Country country = new Country();
        tableData.forEach(elementData -> {
            String data = elementData.text().trim().replace(",", "").replace("+", "");
            addValue(country, column, data.equalsIgnoreCase("N/A") ? "0" : data);
            column++;
        });

        if (country.getName() != null && !country.getName().isEmpty() && !country.getName().matches("(?i)(total:)")) {
            country.setContinent(countryUtil.getContinent(country.getName()));
            return country;
        }
        return null;
    }

    private void addValue(Country country, int index, String value) {
        switch (index) {
            case 0:
                country.setName(value);
                break;
            case 1:
                country.setTotalCases(value.isEmpty() ? 0 : Integer.valueOf(value));
                break;
            case 2:
                country.setTotalNewCasesInTheLast24h(value.isEmpty() ? 0 : Integer.valueOf(value));
                break;
            case 3:
                country.setTotalDeaths(value.isEmpty() ? 0 : Integer.valueOf(value));
                break;
            case 4:
                country.setTotalNewDeathsInTheLast24h(value.isEmpty() ? 0 : Integer.valueOf(value));
                break;
            case 5:
                country.setTotalRecovered(value.isEmpty() ? 0 : Integer.valueOf(value));
                break;
            case 6:
                country.setTotalActiveCases(value.isEmpty() ? 0 : Integer.valueOf(value));
                break;
            case 7:
                country.setTotalCriticalCases(value.isEmpty() ? 0 : Integer.valueOf(value));
                break;
        }
    }

    private ContinentWithCountries transformToContinentAux(String continentName, List<Country> countries) {
        try {

            Country countryFound = countries.stream()
                    .filter(country -> country.getName().equalsIgnoreCase(continentName))
                    .findFirst().get();

            countries.remove(countryFound);

            Continent continent = transformToContinent(countryFound);
            addTotalCountriesToContinent(continent, countries);

            return new ContinentWithCountries(continent, countries);
        } catch (Exception ex) {
            return null;
        }
    }

    private List<Continent> transformToContinentList(List<Country> countries) {
        List<Continent> continentTransformeds = new ArrayList<>();
        countries.forEach(country -> continentTransformeds.add(transformToContinent(country)));

        return continentTransformeds;
    }

    private Continent transformToContinent(Country country) {
        return new Continent((Entity) country);
    }

    private void addTotalCountriesToContinent(Continent continent, List<Country> countries) {
        Long totalCountries = continent.getName().equalsIgnoreCase("world")? countries.size(): countries.stream()
                .filter(country -> country.getContinent().equalsIgnoreCase(continent.getName()))
                .count();

        continent.setTotalCountries(totalCountries.intValue());
    }

}