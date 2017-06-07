package tech.fluff.mtgcounter;

import java.util.ArrayList;
import java.util.Map;


public class Card {
    private ArrayList<Map<String, String>> rules;
    private Map<String, String> card;

    public Card(ArrayList<Map<String, String>> initrules, Map<String, String> initcard) {
        card = initcard;
        rules = initrules;
    }

    public Map<String, String> getCard() {
        return card;
    }

    public ArrayList<Map<String, String>> getRules() {
        return rules;
    }
}
