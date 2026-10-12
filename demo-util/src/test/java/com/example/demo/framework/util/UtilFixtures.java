package com.example.demo.framework.util;

import java.util.Date;
import java.util.List;

/** Public bean types used by reflection-based conversion and document tests. */
public final class UtilFixtures {
    private UtilFixtures() {
    }

    public static class Person {
        private String name;
        private Integer age;
        private Double amount;
        private Boolean active;
        private Date date;
        private Address address;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
        public Boolean getActive() { return active; }
        public void setActive(Boolean active) { this.active = active; }
        public Date getDate() { return date; }
        public void setDate(Date date) { this.date = date; }
        public Address getAddress() { return address; }
        public void setAddress(Address address) { this.address = address; }
    }

    public static class Address {
        private String city;
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
    }

    public static class Report {
        private List<Person> people;
        private Person summary;
        public List<Person> getPeople() { return people; }
        public void setPeople(List<Person> people) { this.people = people; }
        public Person getSummary() { return summary; }
        public void setSummary(Person summary) { this.summary = summary; }
    }

    public static Person person(String name) {
        Person person = new Person();
        person.setName(name);
        person.setAge(18);
        person.setAmount(12.5);
        person.setActive(true);
        Address address = new Address();
        address.setCity("杭州");
        person.setAddress(address);
        return person;
    }
}
