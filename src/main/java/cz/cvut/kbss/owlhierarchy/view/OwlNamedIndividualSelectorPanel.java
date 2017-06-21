package cz.cvut.kbss.owlhierarchy.view;

import cz.cvut.kbss.owlhierarchy.model.OwlNamedIndividualHierarchyProvider;
import java.awt.Color;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.protege.editor.core.ui.view.ViewComponent;
import org.protege.editor.core.ui.view.ViewComponentPlugin;
import org.protege.editor.core.ui.view.ViewComponentPluginAdapter;
import org.protege.editor.core.ui.workspace.Workspace;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProvider;
import org.protege.editor.owl.ui.renderer.OWLSystemColors;
import org.protege.editor.owl.ui.selector.AbstractHierarchySelectorPanel;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

public class OwlNamedIndividualSelectorPanel
    extends AbstractHierarchySelectorPanel<OWLNamedIndividual> {

    private OwlNamedIndividualHierarchyViewComponent vc;

    public OwlNamedIndividualSelectorPanel(
        OWLEditorKit editorKit,
        boolean editable,
        OWLObjectHierarchyProvider<OWLNamedIndividual> hp) {
        super(editorKit, editable, hp);
    }

    protected ViewComponentPlugin getViewComponentPlugin() {

        return new ViewComponentPluginAdapter() {
            public String getLabel() {
                return "Individual Hierarchy";
            }

            public Workspace getWorkspace() {
                return getOWLEditorKit().getWorkspace();
            }

            public ViewComponent newInstance()
                throws ClassNotFoundException, IllegalAccessException, InstantiationException {
                vc = new OwlNamedIndividualHierarchyViewComponent(
                    (OwlNamedIndividualHierarchyProvider) getHierarchyProvider()
                );
                vc.setup(this);
                return vc;
            }

            public Color getBackgroundColor() {
                return OWLSystemColors.getOWLClassColor();
            }
        };
    }

    public void setSelection(OWLNamedIndividual cls) {
        vc.setSelectedEntity(cls);
    }

    public void setSelection(Set<OWLNamedIndividual> clses) {
        vc.setSelectedEntities(clses);
    }

    public OWLNamedIndividual getSelectedObject() {
        return vc.getSelectedEntity();
    }

    public Set<OWLNamedIndividual> getSelectedObjects() {
        return vc.getSelectedEntities();
    }

    public void dispose() {
        vc.dispose();
    }

    public void addSelectionListener(ChangeListener listener) {
        vc.addChangeListener(listener);
    }

    public void removeSelectionListener(ChangeListener listener) {
        vc.removeChangeListener(listener);
    }
}
