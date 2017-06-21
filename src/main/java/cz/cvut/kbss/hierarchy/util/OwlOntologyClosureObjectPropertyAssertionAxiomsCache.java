package cz.cvut.kbss.hierarchy.util;

import java.util.Set;
import javax.annotation.Nonnull;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.parameters.Imports;

public class OwlOntologyClosureObjectPropertyAssertionAxiomsCache extends OwlOntologyAdapter {

    private Set<OWLObjectPropertyAssertionAxiom> axioms;

    private OWLOntologyChangeListener listener = changes -> this.axioms = null;

    public OwlOntologyClosureObjectPropertyAssertionAxiomsCache(OWLOntology o) {
        super(o);
        o.getOWLOntologyManager().addOntologyChangeListener(listener);
    }

    @Override
    public Set<OWLObjectPropertyAssertionAxiom> getAxioms(@Nonnull AxiomType type,
                                                          @Nonnull Imports imports) {
        if (Imports.EXCLUDED.equals(imports) || !type.equals(AxiomType.OBJECT_PROPERTY_ASSERTION)) {
            return super.getAxioms(type,imports);
        }
        if (axioms == null) {
            axioms = super.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION, imports);
        }
        return axioms;
    }
}