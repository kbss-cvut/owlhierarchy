package cz.cvut.kbss.owlhierarchy.view;

import java.util.ArrayList;
import java.util.List;
import org.protege.editor.owl.ui.view.AbstractOWLEntityHierarchyViewComponent;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;

public abstract class AbstractOwlNamedIndividualHierarchyViewComponent
    extends AbstractOWLEntityHierarchyViewComponent<OWLNamedIndividual> {
    private static final long serialVersionUID = -2033744534853698832L;

    protected OWLObject updateView() {
        return updateView(getOWLWorkspace().getOWLSelectionModel().getLastSelectedIndividual());
    }

    public List<OWLNamedIndividual> find(String match) {
        return new ArrayList<>(getOWLModelManager().getOWLEntityFinder()
            .getMatchingOWLIndividuals(match));
    }
}
