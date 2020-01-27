package fi.tampere.mustasaari.mikko.buttongame;

import org.springframework.data.jpa.repository.JpaRepository;

/*
* Repository for user data.
*/

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByName(String name);
}