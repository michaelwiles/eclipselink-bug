package model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonWithType, Long> {

    public List<PersonWithType> findByPersonTypeIn(List<PersonType> types);

    public void findByPersonTypeInAndSurname(List<PersonType> list, Surname surname);
}
