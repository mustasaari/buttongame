package fi.tampere.mustasaari.mikko.buttongame;

import org.springframework.data.jpa.repository.JpaRepository;

/*
* Repository for current Lucky Number.
*/

public interface NumberRepository extends JpaRepository<NumberModel, Long> {
    NumberModel findById(long id);
}