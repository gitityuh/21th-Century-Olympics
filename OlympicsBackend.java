import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * this class implements the IcountrySearcherBackend module
 * and uses its to create a class that utilizes Red black trees to sort through data on country's
 * olympic performances
 */
public class OlympicsBackend implements ICountrySearcherBackend {
    //trees are only public because i need to acces them for the test for now will change back to private after merge
    //will also change into proper redBlackTree type
    public RedBlackTreeBackend<ICountry> goldMedalTree = new RedBlackTreeBackend<>();
    public RedBlackTreeBackend<ICountry> silverMedalTree = new RedBlackTreeBackend<>();
    public RedBlackTreeBackend<ICountry> bronzeMedalTree = new RedBlackTreeBackend<>();
    //private RedBlackTree<ICountry> totalMedalTree;
    public RedBlackTreeBackend<ICountry> countryNameTree = new RedBlackTreeBackend<>();
    private boolean africaToggle = true;
    private boolean asiaToggle = true;
    private boolean ausToggle = true;
    private boolean europeToggle = true;
    private boolean naToggle = true;
    private boolean saToggle = true;


    /**
     * this country is inserted into the redblack trees and is compared to other countries based
     * on the type of medal or alphabetically
     * @param country
     */
    @Override public void addCountry(ICountry country) {
        goldMedalTree.insert(country, country.getGoldMedals());
        silverMedalTree.insert(country, country.getSilverMedals());
        bronzeMedalTree.insert(country, country.getBronzeMedals());
        //totalMedalTree.insert(country, country.getTotalMedals());
        countryNameTree.insert(country, country.getName());
    }

    //I don't think this method is ever used
    //@Override public int getNumberOfCountries() {

      //  return 0;
   // }

    //this is method is also never used
   // @Override public void setMedalType(String medal, boolean filter) {

    //}
    //never used
    //@Override public boolean getMedalFilter(String medal) {
    //    return false;
    //}
    //never used
    //@Override public void toggleMedalFilter(String medal) {

    //}

    /**
     * this method gets the info from theRedBlackTree removes continents that are filtered off
     * and returns the country whose name was provided if it within the avaiable countries
     * otherwise returns null
     * @param name
     * @return
     */
    @Override public ICountry searchByName(String name) {
        //for now the contains method within the IRedBlackTrees interface only returns a boolean if
        // whether, the object exists within the red black tree, can change this to method to return
        //the value if it contains next week when mergeing next week, which might be a good idea
        ArrayList<ICountry> countries = countryNameTree.storeKeyValues(countryNameTree.root);
        //another note the method above storeKeyValues, I added it to my version of the red black tree
        //we can just give it to our algorithm engineer after merging
        //it returns a sorted list from the red black tree of countries
        countries = removeFiltered(countries);
        for (int i = 0; i < countries.size(); i++) {
            if (countries.get(i).getName().toLowerCase() == name.toLowerCase()) {
                return countries.get(i);
            }
        }
        System.out.println(
            "this country is not within the current content filters?/ something went wrong");
        return null;
    }//end search by name

    //this is not used
   // @Override public List<ICountry> outputByTotalMedals() {
    //    ArrayList<ICountry> countriesByTotalMedals =
     //       totalMedalTree.storeKeyValues(totalMedalTree.root);

      //  return null;
    //}

    /**
     * returns a list of the country's in sorted order based on the medal type
     * while also removing filtered continents
     * returns null if the medal type is not valid
     * @param medalType
     * @return
     */
    @Override public List<ICountry> outputByTypeOfMedals(String medalType) {

        if(medalType.toLowerCase().equals("gold")){
            ArrayList<ICountry> gold = goldMedalTree.storeKeyValues(goldMedalTree.root);
            gold = removeFiltered(gold);
            return gold;
        }else if(medalType.toLowerCase().equals("silver")){
            ArrayList<ICountry> silver = silverMedalTree.storeKeyValues(silverMedalTree.root);
            silver = removeFiltered(silver);
            return silver;
        }else if(medalType.toLowerCase().equals("bronze")){
            ArrayList<ICountry> bronze = bronzeMedalTree.storeKeyValues(bronzeMedalTree.root);
            bronze = removeFiltered(bronze);
            return bronze;
        }else{
            System.out.println("invalid medal type returning null");
            return null;
        }

    }

    /**
     * returns a list of countries with the contents filtered to match the continet filter sorted
     * by alphabetical names
     * @return
     */
    @Override public List<ICountry> outputByAlphabeticalName() {
        ArrayList<ICountry> alphabeticalName = countryNameTree.storeKeyValues(countryNameTree.root);
        alphabeticalName = removeFiltered(alphabeticalName);
        return alphabeticalName;
    }

    /**
     * toggles the boolean values of the continentFilters
     *
     * @param number
     */
    @Override public void toggleContinentFilter(Integer number) {
        if(number == 1){
            africaToggle = !africaToggle;
        }else if(number == 2){
            asiaToggle = !asiaToggle;
        }else if(number == 3){
            ausToggle = !ausToggle;
        }else if(number == 4){
            europeToggle = !europeToggle;
        }else if(number == 5){
            naToggle = !naToggle;
        }else if(number == 6){
            saToggle = !saToggle;
        }else{
            System.out.println("invalid number");
        }
    }//end toggle content filters

    /**
     * removes all continents that are toggled off from the list of countries from a red black tree
     * @param countries
     * @return
     */
    public ArrayList<ICountry> removeFiltered(ArrayList<ICountry> countries){
        if(africaToggle == false){
            for(int i = 0; i < countries.size(); i ++){
                if(countries.get(i).getContinent().toLowerCase().equals("africa")){
                    countries.remove(i);
                }
            }
        }else if(asiaToggle == false){
            for(int i = 0; i < countries.size(); i ++){
                if(countries.get(i).getContinent().toLowerCase().equals("asia")){
                    countries.remove(i);
                }
            }
        }else if(ausToggle == false){
            for(int i = 0; i < countries.size(); i ++){
                if(countries.get(i).getContinent().toLowerCase().equals("oceania")){
                    countries.remove(i);
                }
            }
        }else if(europeToggle == false){
            for(int i = 0; i < countries.size(); i ++){
                if(countries.get(i).getContinent().toLowerCase().equals("europe")){
                    countries.remove(i);
                }
            }
        }else if(naToggle == false){
            for(int i = 0; i < countries.size(); i ++){
                if(countries.get(i).getContinent().toLowerCase().equals("north america")){
                    countries.remove(i);
                }
            }
        }else if(saToggle == false){
            for(int i = 0; i < countries.size(); i ++){
                if(countries.get(i).getContinent().toLowerCase().equals("south america")){
                    countries.remove(i);
                }
            }
        }
        return countries;
    }//removeFiltered


    //gold
    //silver
    //bronze
    //total medals
    //alphabetical name



}
