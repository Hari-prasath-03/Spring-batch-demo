package tech.hariprasath.batchdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.hariprasath.batchdemo.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}