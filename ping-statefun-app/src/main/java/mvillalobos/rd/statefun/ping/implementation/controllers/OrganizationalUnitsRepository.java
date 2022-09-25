package mvillalobos.rd.statefun.ping.implementation.controllers;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import mvillalobos.rd.statefun.ping.api.entities.OrganizationalUnit;

@RepositoryRestResource(collectionResourceRel = "organizationalUnits", path = "organizationalUnits")
public interface OrganizationalUnitsRepository extends PagingAndSortingRepository<OrganizationalUnit, Long> {
}
