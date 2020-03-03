package lesson3;

import java.util.*;

public class PhoneBook {
    private TreeMap<String, Set<String>> records;

    public PhoneBook() {
        records = new TreeMap<>();
    }

    public void add(String surname, String phoneNumber)
    {
        get(surname).add(phoneNumber);
    }

    public Set<String> get(String surname){
        return records.computeIfAbsent(surname, key -> new HashSet<>());
    }

    public Set<String> getSurnamesInPhoneBook(){
        return records.keySet();
    }
}

