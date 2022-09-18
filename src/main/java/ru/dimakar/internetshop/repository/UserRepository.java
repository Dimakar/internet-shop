package ru.dimakar.internetshop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dimakar.internetshop.model.RoleModel;
import ru.dimakar.internetshop.model.UserModel;

import java.util.List;

public interface UserRepository extends CrudRepository<UserModel, Long> {
    List<UserModel> findAll();

    UserModel findUserModelByEmailIgnoreCase(String email);

    List<UserModel> findAllByRole(RoleModel roleModel);
}
