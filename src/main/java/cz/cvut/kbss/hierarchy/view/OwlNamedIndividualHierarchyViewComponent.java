package cz.cvut.kbss.hierarchy.view;

import cz.cvut.kbss.hierarchy.model.OwlNamedIndividualHierarchyProvider;
import cz.cvut.kbss.hierarchy.model.OwlNamedIndividualInferredHierarchyProvider;
import java.awt.Color;
import java.util.Optional;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerListener;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProvider;
import org.protege.editor.owl.model.inference.NoOpReasoner;
import org.protege.editor.owl.model.selection.OWLSelectionModelListener;
import org.protege.editor.owl.ui.framelist.OWLFrameList;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OwlNamedIndividualHierarchyViewComponent
    extends AbstractOwlNamedIndividualHierarchyViewComponent {
    private static final long serialVersionUID = 8712815067223088069L;

    private OwlNamedIndividualHierarchyProvider provider;

    public OwlNamedIndividualHierarchyViewComponent(
        OwlNamedIndividualHierarchyProvider provider) {
        this.provider = provider;
    }

    private OWLSelectionModelListener l1 = () -> rebuildAsNecessary();

    private OWLModelManagerListener l2 = event -> {
        try {
            if (event.isType(EventType.ACTIVE_ONTOLOGY_CHANGED)
                || event.isType(EventType.ONTOLOGY_RELOADED)
                || event.isType(EventType.REASONER_CHANGED)) {
                rebuildAsNecessary();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    @Override
    protected void performExtraInitialisation() throws Exception {
        getOWLWorkspace().getOWLSelectionModel().addListener(l1);
        getOWLWorkspace().getOWLModelManager().addListener(l2);
    }

    private OWLReasoner getCanonicalReasoner() {
        return getOWLModelManager().getReasoner() instanceof NoOpReasoner
            ? null
            : getOWLModelManager().getReasoner();
    }

    private void rebuildAsNecessary() {
        try {
            if (getCanonicalReasoner() != null) {
                getTree().setBackground(OWLFrameList.INFERRED_BG_COLOR);
            } else {
                getTree().setBackground(Color.WHITE);
            }

            if (provider instanceof OwlNamedIndividualInferredHierarchyProvider) {
                ((OwlNamedIndividualInferredHierarchyProvider) provider)
                    .setReasoner(getCanonicalReasoner());
            }
            provider.setProperty(getOWLWorkspace().getOWLSelectionModel()
                .getLastSelectedObjectProperty());
            provider.setOntologies(getOWLModelManager().getActiveOntologies());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected OWLObjectHierarchyProvider<OWLNamedIndividual> getHierarchyProvider() {
        return provider;
    }

    @Override
    protected Optional<OWLObjectHierarchyProvider<OWLNamedIndividual>>
        getInferredHierarchyProvider() {
        return Optional.empty();
    }

    @Override
    public void disposeView() {
        super.disposeView();
        getOWLWorkspace().getOWLSelectionModel().removeListener(l1);
        getOWLWorkspace().getOWLModelManager().removeListener(l2);
    }
}
