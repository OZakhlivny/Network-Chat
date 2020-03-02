package lesson3;

import java.util.*;

public class PhoneBook {
    private HashMap<String, String> records;

    public PhoneBook() {
        records = new HashMap<>();
    }

    public void add(String phoneNumber, String surname){
        records.put(phoneNumber, surname);
    }

    public Set<String> get(String surname){
        Set<String> phoneNumbers = new HashSet<>();
        for(Map.Entry<String, String> record : records.entrySet()) {
            if (Objects.equals(surname, record.getValue())) phoneNumbers.add(record.getKey());
        }

        return phoneNumbers;
    }
}

