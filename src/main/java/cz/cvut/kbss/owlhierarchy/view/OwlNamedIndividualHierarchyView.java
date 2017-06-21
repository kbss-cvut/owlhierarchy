package cz.cvut.kbss.owlhierarchy.view;

import cz.cvut.kbss.owlhierarchy.model.OwlNamedIndividualHierarchyProvider;
import cz.cvut.kbss.owlhierarchy.model.OwlNamedIndividualInferredHierarchyProvider;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import org.protege.editor.owl.ui.view.AbstractActiveOntologyViewComponent;
import org.semanticweb.owlapi.model.OWLOntology;

public class OwlNamedIndividualHierarchyView extends
    AbstractActiveOntologyViewComponent {
    private static final long serialVersionUID = 1L;

    private OwlNamedIndividualHierarchyProvider provider;

    protected void initialiseOntologyView() throws Exception {
        provider = new OwlNamedIndividualInferredHierarchyProvider();
        initComponents();
    }

    protected void disposeOntologyView() {

    }

    private void initComponents() {
        final JPanel pnlControls = new JPanel(new FlowLayout());
        pnlControls.setBorder(BorderFactory.createEtchedBorder());

        final JCheckBox chbInverse = new JCheckBox("inverse property");
        chbInverse.addActionListener(e -> {
            provider.setInverse(chbInverse.isSelected());
        });
        pnlControls.add(chbInverse);

        final JCheckBox chbHideOrphans = new JCheckBox("hide orphans");
        chbHideOrphans.addActionListener(e -> {
            provider.setHideOrphans(chbHideOrphans.isSelected());
        });
        pnlControls.add(chbHideOrphans);

        final JPanel pnlMain = new JPanel(new BorderLayout());
        OwlNamedIndividualSelectorPanel view = new OwlNamedIndividualSelectorPanel(
            getOWLEditorKit(),false, provider);
        view.setBorder(BorderFactory.createEtchedBorder());
        pnlMain.add(pnlControls, BorderLayout.NORTH);
        pnlMain.add(view, BorderLayout.CENTER);
        this.setLayout(new BorderLayout());
        this.add(pnlMain, BorderLayout.CENTER);
    }

    @Override
    protected void updateView(OWLOntology activeOntology) throws Exception {
        // TODO Auto-generated method stub
    }
}
