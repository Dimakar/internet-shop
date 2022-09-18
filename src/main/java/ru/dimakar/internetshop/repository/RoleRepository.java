package ru.dimakar.internetshop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dimakar.internetshop.model.RoleModel;

public interface RoleRepository extends CrudRepository<RoleModel, Long> {
    RoleModel findRoleModelByName(String name);
}
