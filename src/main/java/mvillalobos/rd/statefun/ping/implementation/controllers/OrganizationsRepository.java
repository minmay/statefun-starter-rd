package mvillalobos.rd.statefun.ping.implementation.controllers;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import mvillalobos.rd.statefun.ping.api.entities.Organization;

@RepositoryRestResource(collectionResourceRel = "organizations", path = "organizations")
public interface OrganizationsRepository extends PagingAndSortingRepository<Organization, Long> {
}
