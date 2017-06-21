package cz.cvut.kbss.hierarchy.model;

import java.util.Set;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OwlNamedIndividualInferredHierarchyProvider extends
    OwlNamedIndividualHierarchyProvider {

    private OWLReasoner reasoner;

    protected Set<OWLNamedIndividual> getSubs(final OWLNamedIndividual i, final OWLOntology o) {
        if (reasoner != null) {
            final OWLObjectPropertyExpression e = inverse
                ? o.getOWLOntologyManager().getOWLDataFactory().getOWLObjectInverseOf(property)
                : property;
            return reasoner.getObjectPropertyValues(i, e).getFlattened();
        } else {
            return super.getSubs(i,o);
        }
    }

    /**
     * Sets the reasoner and recomputes the hierarchy.
     *
     * @param reasoner an active OWL reasoner to be set, or null.
     */
    public void setReasoner(OWLReasoner reasoner) {
        logger.trace(m, "Setting reasoner ", reasoner);
        this.reasoner = reasoner;
        compute();
    }
}
