package io.papermc.hangar.db.dao.internal.table.roles;

import io.papermc.hangar.db.mappers.RoleMapperFactory;
import io.papermc.hangar.model.db.roles.OrganizationRoleTable;
import org.jdbi.v3.sqlobject.config.RegisterColumnMapperFactory;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Timestamped;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

@Repository
@RegisterConstructorMapper(OrganizationRoleTable.class)
@RegisterColumnMapperFactory(RoleMapperFactory.class)
public interface OrganizationRolesDAO extends IRolesDAO<OrganizationRoleTable> {

    @Override
    @Timestamped
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO user_organization_roles (created_at, user_id, role_type, organization_id, accepted) " +
               "VALUES (:now, :userId, :roleType, :organizationId, :accepted)")
    OrganizationRoleTable insert(@BindBean OrganizationRoleTable table);

    @Override
    @SqlUpdate("UPDATE user_organization_roles SET accepted = :accepted WHERE id = :id")
    void update(@BindBean OrganizationRoleTable table);

    @Override
    @SqlUpdate("DELETE FROM user_organization_roles WHERE organization_id = :organizationId AND user_id = :userId")
    void delete(@BindBean OrganizationRoleTable table);

    @Override
    @SqlQuery("SELECT * FROM user_organization_roles WHERE id = :id AND user_id = :userId")
    OrganizationRoleTable getTable(long id, long userId);

    @Override
    @SqlQuery("SELECT * FROM user_organization_roles WHERE organization_id = :organizationId AND user_id = :userId")
    OrganizationRoleTable getTableByPrincipal(long organizationId, long userId);

    @Override
    @SqlQuery("SELECT * FROM user_organization_roles WHERE organization_id = :organizationId AND user_id = :userId")
    OrganizationRoleTable getTable(@BindBean OrganizationRoleTable table);
}
