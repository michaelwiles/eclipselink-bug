package model;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles("mysql")
public class SimpleTest {

    @Autowired
    PersonRepository repository;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void TestLookupWithMoreOneEnumInTheFilter() {
        System.out.println(repository.findAll());
        Surname surname = new Surname();
        em.persist(surname);
        repository.findByPersonTypeInAndSurname(Lists.newArrayList(PersonType.MALE), surname);
        //return titleRepository.findByStatusInAndTenant(statusList, tenant);
    }

    @Test
    @Transactional
    public void TestLookupWithMoreThanOneEnumInTheFilter() {
        System.out.println(repository.findAll());
        Surname surname = new Surname();
        em.persist(surname);
        repository.findByPersonTypeInAndSurname(Lists.newArrayList(PersonType.MALE, PersonType.FEMALE), surname);
        //return titleRepository.findByStatusInAndTenant(statusList, tenant);
    }
}
