package ourbusinessproject;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EnterpriseProjectService {

    @PersistenceContext
    private EntityManager entityManager;

    public Project saveProjectForEnterprise(Project project, Enterprise enterprise) {
    	// TODO supprimer le projet dans son ancienne entreprise.
        saveEnterprise(enterprise);
        project.setEnterprise(enterprise);
        enterprise.addProject(project);
        Project newProject = entityManager.merge(project);
        return newProject;
    }

    public Enterprise saveEnterprise(Enterprise enterprise) {
    	Enterprise newEnterprise = entityManager.merge(enterprise);
        return newEnterprise;
    }

    public Project findProjectById(Long id) {
        return entityManager.find(Project.class, id);
    }

    public Enterprise findEnterpriseById(Long id) {
        return entityManager.find(Enterprise.class, id);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Project> findAllProjects() {
        TypedQuery<Project> query = entityManager.createQuery("select p from Project p join fetch p.enterprise order by p.title", Project.class);
        return query.getResultList();
    }
}
