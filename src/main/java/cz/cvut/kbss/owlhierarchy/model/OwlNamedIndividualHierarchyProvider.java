package cz.cvut.kbss.owlhierarchy.model;

import java.util.HashSet;
import java.util.Set;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.parameters.Imports;

public class OwlNamedIndividualHierarchyProvider
    extends AbstractOwlNamedIndividualHierarchyProvider {

    protected Set<OWLNamedIndividual> getSubs(
        final OWLNamedIndividual i,
        final OWLOntology ontology) {
        final Set<OWLNamedIndividual> set = new HashSet<>();

        for (final OWLAxiom axiom : ontology.getAxioms(
            AxiomType.OBJECT_PROPERTY_ASSERTION,
            Imports.INCLUDED)) {
            final OWLObjectPropertyAssertionAxiom a = ((OWLObjectPropertyAssertionAxiom) axiom);

            if (a.getProperty() != property) {
                continue;
            }

            if (!a.containsEntityInSignature(i)) {
                continue;
            }

            if (a.getSubject().equals(i) && !inverse) {
                set.add(a.getObject().asOWLNamedIndividual());
            } else if (a.getObject().equals(i) && inverse) {
                set.add(a.getSubject().asOWLNamedIndividual());
            }
        }

        return set;
    }
}
