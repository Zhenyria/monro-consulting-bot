package ru.zhenyria.monro_consulting_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zhenyria.monro_consulting_bot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
